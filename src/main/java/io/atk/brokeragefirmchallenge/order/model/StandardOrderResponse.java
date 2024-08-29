package io.atk.brokeragefirmchallenge.order.model;

import io.atk.brokeragefirmchallenge.account.model.StandardAccountResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StandardOrderResponse {
    private String id;
    private String createdAt;
    private String updatedAt;
    private StandardAccountResponse account;
    private String assetName;
    private String side;
    private long size;
    private String price;
    private String status;
}
