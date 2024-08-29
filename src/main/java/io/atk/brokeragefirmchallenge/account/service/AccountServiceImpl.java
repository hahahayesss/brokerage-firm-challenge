package io.atk.brokeragefirmchallenge.account.service;

import io.atk.brokeragefirmchallenge.account.model.Account;
import io.atk.brokeragefirmchallenge.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public Account getById(String accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("account not found"));
    }

    @Override
    public Account getByUsername(String username) {
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("account not found"));
    }
}
