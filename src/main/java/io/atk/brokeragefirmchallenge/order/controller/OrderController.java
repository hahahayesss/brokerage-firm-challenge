package io.atk.brokeragefirmchallenge.order.controller;

import io.atk.brokeragefirmchallenge.order.mapper.OrderMapper;
import io.atk.brokeragefirmchallenge.order.model.CreateOrderRequest;
import io.atk.brokeragefirmchallenge.order.model.Order;
import io.atk.brokeragefirmchallenge.order.model.StandardOrderResponse;
import io.atk.brokeragefirmchallenge.order.service.OrderService;
import io.atk.brokeragefirmchallenge.security.model.AdminOnly;
import io.atk.brokeragefirmchallenge.security.model.OwnOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account/{accountId}/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderMapper orderMapper;
    private final OrderService orderService;

    @OwnOnly
    @GetMapping
    public ResponseEntity<?> get(
            @PathVariable String accountId,
            @RequestParam(value = "createdAt.gt", required = false, defaultValue = "0") long start,
            @RequestParam(value = "createdAt.lt", required = false, defaultValue = "" + Long.MAX_VALUE) long end) {
        List<Order> orders = orderService.getByAccountAndTimeRange(accountId, start, end);
        return ResponseEntity.ok(orders.stream().map(orderMapper::map).toList());
    }

    @OwnOnly
    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable String accountId, @Validated @RequestBody CreateOrderRequest request) {
        request.setAccountId(accountId);
        Order order = orderService.save(request);
        return ResponseEntity.ok(orderMapper.map(order));
    }

    @AdminOnly
    @PatchMapping("/{orderId}")
    public ResponseEntity<?> match(@PathVariable String accountId, @PathVariable String orderId) {
        Order order = orderService.match(orderId);
        return ResponseEntity.ok(orderMapper.map(order));
    }

    @OwnOnly
    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> cancel(@PathVariable String accountId, @PathVariable String orderId) {
        Order order = orderService.cancel(orderId);
        return ResponseEntity.ok(orderMapper.map(order));
    }
}
