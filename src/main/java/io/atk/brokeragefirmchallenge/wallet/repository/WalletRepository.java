package io.atk.brokeragefirmchallenge.wallet.repository;

import io.atk.brokeragefirmchallenge.wallet.model.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends MongoRepository<Wallet, String> {

    boolean existsByIdAndAccountId(String id, String accountId);

    Optional<Wallet> findByIdAndAccountId(String id, String accountId);
}
