package io.atk.brokeragefirmchallenge.asset.model;

import io.atk.brokeragefirmchallenge.model.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Asset extends Entity {
    private String accountId;
    private String name;
    private long size;
    private long usableSize;
}
