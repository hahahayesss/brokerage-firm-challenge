package io.atk.brokeragefirmchallenge.order.model;

import io.atk.brokeragefirmchallenge.model.Side;
import io.atk.brokeragefirmchallenge.model.Status;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.math.BigDecimal;
import java.util.function.Supplier;

@Data
public class CreateOrderRequest implements Supplier<Order> {

    @NotNull
    private String asset;

    @NotNull
    private Side side;

    @NotNull
    private long size;

    @NotNull
    private BigDecimal price;

    @Null
    private String accountId;

    @Override
    public Order get() {
        return Order.builder()
                .accountId(getAccountId())
                .assetName(getAsset())
                .side(getSide())
                .size(getSize())
                .price(getPrice())
                .build();
    }
}
