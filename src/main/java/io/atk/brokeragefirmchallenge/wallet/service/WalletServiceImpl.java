package io.atk.brokeragefirmchallenge.wallet.service;

import io.atk.brokeragefirmchallenge.wallet.model.Wallet;
import io.atk.brokeragefirmchallenge.wallet.model.WalletOperation;
import io.atk.brokeragefirmchallenge.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private static volatile Set<String> LOCKED_ACCOUNTS = new HashSet<>();

    private final MongoTemplate mongoTemplate;
    private final WalletRepository walletRepository;

    /**
     * Should be use optimistic lock
     */
    @Override
    public Wallet addAmount(String accountId, String walletId, Supplier<WalletOperation> supplier) {
        waitUntilWalletAvailable(walletId);
        lockWallet(walletId);

        if (walletRepository.existsByIdAndAccountId(walletId, accountId))
            throw new RuntimeException("wallet not found");

        WalletOperation walletOperation = generateStandardWalletOperation(supplier);
        boolean acknowledged = updateWallet(walletId, walletOperation);
        if (!acknowledged) throw new RuntimeException("somethings went wrong");

        unlockWallet(walletId);

        return walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("wallet not found"));
    }

    /**
     * Should be use optimistic lock
     */
    @Override
    public Wallet subtractAmount(String accountId, String walletId, Supplier<WalletOperation> supplier) {
        waitUntilWalletAvailable(walletId);
        lockWallet(walletId);

        if (walletRepository.existsByIdAndAccountId(walletId, accountId))
            throw new RuntimeException("wallet not found");

        WalletOperation walletOperation = generateStandardWalletOperation(supplier);
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("wallet not found"));
        int result = wallet.getOperations().stream()
                .map(WalletOperation::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .compareTo(walletOperation.getAmount());
        if (result < 0) throw new RuntimeException("not enough balance in the wallet");

        walletOperation.setAmount(walletOperation.getAmount().negate());
        boolean acknowledged = updateWallet(walletId, walletOperation);
        if (!acknowledged) throw new RuntimeException("somethings went wrong");

        unlockWallet(walletId);

        return walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("wallet not found"));
    }

    @Override
    public void waitUntilWalletAvailable(String walletId) {
        while (LOCKED_ACCOUNTS.contains(walletId)) continue;
    }

    @Override
    public void lockWallet(String walletId) {
        LOCKED_ACCOUNTS.add(walletId);
    }

    @Override
    public void unlockWallet(String walletId) {
        LOCKED_ACCOUNTS.remove(walletId);
    }

    private synchronized boolean updateWallet(String walletId, WalletOperation operation) {
        return mongoTemplate.updateFirst(
                new Query(Criteria.where("id").is(new ObjectId(walletId))),
                new Update().push("operations", operation),
                Wallet.class
        ).wasAcknowledged();
    }

    private WalletOperation generateStandardWalletOperation(Supplier<WalletOperation> supplier) {
        WalletOperation walletOperation = supplier.get();
        walletOperation.setId(UUID.randomUUID().toString());
        walletOperation.setTimestamp(System.currentTimeMillis());
        return walletOperation;
    }
}
