package io.atk.brokeragefirmchallenge.order.repository;

import io.atk.brokeragefirmchallenge.order.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByAccountIdAndCreatedAtGreaterThanAndCreatedAtLessThan(String accountId, long start, long end);
}
