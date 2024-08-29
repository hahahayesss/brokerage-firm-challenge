package io.atk.brokeragefirmchallenge.asset.service;

import io.atk.brokeragefirmchallenge.asset.model.Asset;
import io.atk.brokeragefirmchallenge.asset.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {
    private final AssetRepository assetRepository;

    @Override
    public List<Asset> getByAccountId(String accountId) {
        return assetRepository.findAllByAccountId(accountId);
    }

    @Override
    public Asset add(String accountId, String name, long size) {
        Asset asset = assetRepository.findByAccountIdAndName(accountId, name)
                .orElseGet(() -> {
                    Asset tempAsset = new Asset();
                    tempAsset.setCreatedAt(System.currentTimeMillis());
                    tempAsset.setAccountId(accountId);
                    tempAsset.setName(name);
                    return tempAsset;
                });
        asset.setUpdatedAt(System.currentTimeMillis());
        asset.setUsableSize(asset.getUsableSize() + size);
        if (asset.getUsableSize() > asset.getSize()) asset.setSize(asset.getUsableSize());
        return assetRepository.save(asset);
    }

    /**
     * Should check assetUsableSize is zero,
     * -> if it is, asset should be deleted from account
     * -> if it is not, asset should be reduced
     */
    @Override
    public Asset subtract(String accountId, String name, long size) {
        Asset asset = assetRepository.findByAccountIdAndName(accountId, name)
                .orElseThrow(() -> new RuntimeException("asset not found"));
        asset.setUsableSize(asset.getUsableSize() - size);
        if (asset.getUsableSize() < 0)
            throw new RuntimeException("asset sie can't be negative");
        asset.setUpdatedAt(System.currentTimeMillis());
        return assetRepository.save(asset);
    }
}
