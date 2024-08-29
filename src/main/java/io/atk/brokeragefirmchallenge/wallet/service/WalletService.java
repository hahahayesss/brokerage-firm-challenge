package io.atk.brokeragefirmchallenge.wallet.service;

import io.atk.brokeragefirmchallenge.wallet.model.Wallet;
import io.atk.brokeragefirmchallenge.wallet.model.WalletOperation;

import java.util.function.Supplier;

public interface WalletService {

    Wallet addAmount(String accountId, String walletId, Supplier<WalletOperation> supplier);

    Wallet subtractAmount(String accountId, String walletId, Supplier<WalletOperation> supplier);

    void waitUntilWalletAvailable(String walletId);

    void lockWallet(String walletId);

    void unlockWallet(String walletId);
}
