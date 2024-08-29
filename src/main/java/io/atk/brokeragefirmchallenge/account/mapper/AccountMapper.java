package io.atk.brokeragefirmchallenge.account.mapper;

import io.atk.brokeragefirmchallenge.account.model.Account;
import io.atk.brokeragefirmchallenge.account.model.StandardAccountResponse;
import io.atk.brokeragefirmchallenge.utils.DateTimeUtils;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public StandardAccountResponse map(Account account) {
        return StandardAccountResponse.builder()
                .id(account.getId())
                .createdAt(DateTimeUtils.parse(account.getCreatedAt()).toString())
                .updatedAt(DateTimeUtils.parse(account.getUpdatedAt()).toString())
                .username(account.getUsername())
                .build();
    }
}
