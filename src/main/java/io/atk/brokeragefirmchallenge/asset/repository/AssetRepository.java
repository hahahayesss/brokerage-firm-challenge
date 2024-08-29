package io.atk.brokeragefirmchallenge.asset.repository;

import io.atk.brokeragefirmchallenge.asset.model.Asset;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends MongoRepository<Asset, String> {

    boolean existsByAccountIdAndName(String accountId, String name);

    Optional<Asset> findByAccountIdAndName(String accountId, String name);

    List<Asset> findAllByAccountId(String accountId);
}
