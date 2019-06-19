package com.maybe.maybe.repository;

import com.maybe.maybe.entity.Order;
import com.maybe.maybe.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);
}