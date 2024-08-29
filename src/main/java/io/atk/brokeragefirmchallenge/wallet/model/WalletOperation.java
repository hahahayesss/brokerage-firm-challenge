package io.atk.brokeragefirmchallenge.wallet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletOperation {
    private String id;
    private long timestamp;
    private BigDecimal amount;
    private WalletOperationType type;
}
