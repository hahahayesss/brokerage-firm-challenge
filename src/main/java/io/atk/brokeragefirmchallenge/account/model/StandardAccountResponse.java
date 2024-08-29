package io.atk.brokeragefirmchallenge.account.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StandardAccountResponse {
    private String id;
    private String createdAt;
    private String updatedAt;
    private String username;
}
