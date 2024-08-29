package io.atk.brokeragefirmchallenge.account.repository;

import io.atk.brokeragefirmchallenge.account.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

    Optional<Account> findByUsername(String username);
}
