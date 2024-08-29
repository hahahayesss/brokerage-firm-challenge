package io.atk.brokeragefirmchallenge.wallet.controller;

import io.atk.brokeragefirmchallenge.security.model.OwnOnly;
import io.atk.brokeragefirmchallenge.wallet.mapper.WalletMapper;
import io.atk.brokeragefirmchallenge.wallet.model.Wallet;
import io.atk.brokeragefirmchallenge.wallet.model.WalletOperationRequest;
import io.atk.brokeragefirmchallenge.wallet.model.WalletOperationType;
import io.atk.brokeragefirmchallenge.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/{accountId}/wallet")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;
    private final WalletMapper walletMapper;

    @OwnOnly
    @PostMapping("/{walletId}/deposit")
    public ResponseEntity<?> deposit(
            @PathVariable String accountId, @PathVariable String walletId,
            @Validated @RequestBody WalletOperationRequest request) {
        request.setType(WalletOperationType.MANUEL);
        Wallet wallet = walletService.addAmount(accountId, walletId, request);
        return ResponseEntity.ok(walletMapper.map(wallet));
    }

    @OwnOnly
    @PostMapping("/{walletId}/withdraw")
    public ResponseEntity<?> withdraw(
            @PathVariable String accountId, @PathVariable String walletId,
            @Validated @RequestBody WalletOperationRequest request) {
        request.setType(WalletOperationType.MANUEL);
        Wallet wallet = walletService.subtractAmount(accountId, walletId, request);
        return ResponseEntity.ok(walletMapper.map(wallet));
    }
}
