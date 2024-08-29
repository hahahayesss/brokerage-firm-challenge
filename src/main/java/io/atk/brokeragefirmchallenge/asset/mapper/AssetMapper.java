package io.atk.brokeragefirmchallenge.asset.mapper;

import io.atk.brokeragefirmchallenge.asset.model.Asset;
import io.atk.brokeragefirmchallenge.asset.model.StandardAssetResponse;
import io.atk.brokeragefirmchallenge.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssetMapper {

    public StandardAssetResponse map(Asset asset) {
        return StandardAssetResponse.builder()
                .id(asset.getId())
                .createdAt(DateTimeUtils.parse(asset.getCreatedAt()).toString())
                .updatedAt(DateTimeUtils.parse(asset.getUpdatedAt()).toString())
                .name(asset.getName())
                .size(asset.getSize())
                .usableSize(asset.getUsableSize())
                .build();
    }
}
