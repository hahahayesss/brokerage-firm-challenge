package io.atk.brokeragefirmchallenge.account.service;

import io.atk.brokeragefirmchallenge.account.model.Account;
import org.springframework.stereotype.Service;

public interface AccountService {

    Account getById(String accountId);

    Account getByUsername(String username);
}
