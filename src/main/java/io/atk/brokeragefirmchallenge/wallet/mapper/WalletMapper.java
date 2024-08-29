package io.atk.brokeragefirmchallenge.wallet.mapper;

import io.atk.brokeragefirmchallenge.utils.DateTimeUtils;
import io.atk.brokeragefirmchallenge.wallet.model.StandardWalletResponse;
import io.atk.brokeragefirmchallenge.wallet.model.Wallet;
import io.atk.brokeragefirmchallenge.wallet.model.WalletOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class WalletMapper {

    public StandardWalletResponse map(Wallet wallet) {
        return StandardWalletResponse.builder()
                .id(wallet.getId())
                .createdAt(DateTimeUtils.parse(wallet.getCreatedAt()).toString())
                .updatedAt(DateTimeUtils.parse(wallet.getUpdatedAt()).toString())
                .total(wallet.getOperations().stream()
                               .map(WalletOperation::getAmount)
                               .reduce(BigDecimal.ZERO, BigDecimal::add))
                .build();
    }
}
