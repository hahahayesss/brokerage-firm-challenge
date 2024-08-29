package io.atk.brokeragefirmchallenge.asset.service;

import io.atk.brokeragefirmchallenge.asset.model.Asset;

import java.util.List;

public interface AssetService {

    List<Asset> getByAccountId(String accountId);

    Asset add(String accountId, String name, long size);

    Asset subtract(String accountId, String name, long size);
}
