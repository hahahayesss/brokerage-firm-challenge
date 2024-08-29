package io.atk.brokeragefirmchallenge.asset.controller;

import io.atk.brokeragefirmchallenge.asset.mapper.AssetMapper;
import io.atk.brokeragefirmchallenge.asset.model.Asset;
import io.atk.brokeragefirmchallenge.asset.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account/{accountId}/asset")
@RequiredArgsConstructor
public class AssetController {
    private final AssetService assetService;
    private final AssetMapper assetMapper;

    @GetMapping
    public ResponseEntity<?> get(@PathVariable String accountId) {
        List<Asset> assets = assetService.getByAccountId(accountId);
        return ResponseEntity.ok(assets.stream().map(assetMapper::map).toList());
    }
}
