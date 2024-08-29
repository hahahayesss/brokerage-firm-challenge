package io.atk.brokeragefirmchallenge.wallet.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.math.BigDecimal;
import java.util.function.Supplier;

@Data
public class WalletOperationRequest implements Supplier<WalletOperation> {

    @NotNull
    @Min(value = 1)
    private BigDecimal amount;

    @Null
    private WalletOperationType type;

    @Override
    public WalletOperation get() {
        return WalletOperation.builder()
                .amount(getAmount())
                .type(getType())
                .build();
    }
}
