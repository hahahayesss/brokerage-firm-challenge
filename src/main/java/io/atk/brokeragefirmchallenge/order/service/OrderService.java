package io.atk.brokeragefirmchallenge.order.service;

import io.atk.brokeragefirmchallenge.order.model.Order;

import java.util.List;
import java.util.function.Supplier;

public interface OrderService {

    List<Order> getByAccountAndTimeRange(String accountId, long start, long end);

    Order save(Supplier<Order> supplier);

    Order match(String orderId);

    Order cancel(String orderId);
}
