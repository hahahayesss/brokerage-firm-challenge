package io.atk.brokeragefirmchallenge.order.mapper;

import io.atk.brokeragefirmchallenge.account.mapper.AccountMapper;
import io.atk.brokeragefirmchallenge.account.service.AccountService;
import io.atk.brokeragefirmchallenge.order.model.Order;
import io.atk.brokeragefirmchallenge.order.model.StandardOrderResponse;
import io.atk.brokeragefirmchallenge.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final AccountMapper accountMapper;
    private final AccountService accountService;

    public StandardOrderResponse map(Order order) {
        return StandardOrderResponse.builder()
                .id(order.getId())
                .createdAt(DateTimeUtils.parse(order.getCreatedAt()).toString())
                .updatedAt(DateTimeUtils.parse(order.getUpdatedAt()).toString())
                .account(accountMapper.map(accountService.getById(order.getAccountId())))
                .assetName(order.getAssetName())
                .side(order.getSide().toString())
                .size(order.getSize())
                .price(order.getPrice().toString())
                .status(order.getStatus().toString())
                .build();
    }
}
