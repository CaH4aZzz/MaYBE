package com.maybe.maybe.repository;

import com.maybe.maybe.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}