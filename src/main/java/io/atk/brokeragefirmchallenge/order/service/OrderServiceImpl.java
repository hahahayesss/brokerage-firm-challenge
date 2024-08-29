package io.atk.brokeragefirmchallenge.order.service;

import io.atk.brokeragefirmchallenge.asset.service.AssetService;
import io.atk.brokeragefirmchallenge.model.Side;
import io.atk.brokeragefirmchallenge.model.Status;
import io.atk.brokeragefirmchallenge.order.model.Order;
import io.atk.brokeragefirmchallenge.order.repository.OrderRepository;
import io.atk.brokeragefirmchallenge.wallet.model.Wallet;
import io.atk.brokeragefirmchallenge.wallet.model.WalletOperation;
import io.atk.brokeragefirmchallenge.wallet.model.WalletOperationType;
import io.atk.brokeragefirmchallenge.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final AssetService assetService;
    private final WalletService walletService;
    private final OrderRepository orderRepository;

    @Override
    public List<Order> getByAccountAndTimeRange(String accountId, long start, long end) {
        return orderRepository.findByAccountIdAndCreatedAtGreaterThanAndCreatedAtLessThan(accountId, start, end);
    }

    @Override
    public Order save(Supplier<Order> supplier) {
        Order order = supplier.get();
        order.setCreatedAt(System.currentTimeMillis());
        order.setStatus(Status.PENDING);
        return orderRepository.save(order);
    }

    /**
     * Should write rollback for transactional
     */
    @Override
    @Transactional
    public synchronized Order match(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("order not found"));

        if (order.getSide() == Side.BUY) {
            WalletOperation operation = WalletOperation.builder()
                    .amount(order.getPrice().multiply(BigDecimal.valueOf(order.getSize())))
                    .type(WalletOperationType.SYSTEM)
                    .build();
            walletService.subtractAmount(order.getAccountId(), order.getWalletId(), () -> operation);
            assetService.add(order.getAccountId(), order.getAssetName(), order.getSize());

        } else if (order.getSide() == Side.SELL) {
            WalletOperation operation = WalletOperation.builder()
                    .amount(order.getPrice().multiply(BigDecimal.valueOf(order.getSize())))
                    .type(WalletOperationType.SYSTEM)
                    .build();
            walletService.addAmount(order.getAccountId(), order.getWalletId(), () -> operation);
            assetService.subtract(order.getAccountId(), order.getAssetName(), order.getSize());
        }

        order.setUpdatedAt(System.currentTimeMillis());
        order.setStatus(Status.MATCHED);
        return orderRepository.save(order);
    }

    @Override
    public synchronized Order cancel(String orderId) {
        return update(orderId, order -> order.setStatus(Status.CANCELED));
    }

    private Order update(final String orderId, Consumer<Order> updater) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("order not found"));
        updater.accept(order);
        order.setId(orderId); // to be sure not changed
        order.setUpdatedAt(System.currentTimeMillis());
        return orderRepository.save(order);
    }
}
