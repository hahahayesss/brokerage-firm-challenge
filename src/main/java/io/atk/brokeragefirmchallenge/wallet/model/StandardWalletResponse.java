package io.atk.brokeragefirmchallenge.wallet.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class StandardWalletResponse {
    private String id;
    private String createdAt;
    private String updatedAt;
    private BigDecimal total;
}
