package io.atk.brokeragefirmchallenge.asset.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StandardAssetResponse {
    private String id;
    private String createdAt;
    private String updatedAt;
    private String name;
    private long size;
    private long usableSize;
}
