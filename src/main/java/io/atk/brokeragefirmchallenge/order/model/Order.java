package io.atk.brokeragefirmchallenge.order.model;

import io.atk.brokeragefirmchallenge.model.Entity;
import io.atk.brokeragefirmchallenge.model.Side;
import io.atk.brokeragefirmchallenge.model.Status;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Order extends Entity {
    private String accountId;
    private String walletId;
    private String assetName;
    private Side side;
    private long size;
    private BigDecimal price;
    private Status status;
}
