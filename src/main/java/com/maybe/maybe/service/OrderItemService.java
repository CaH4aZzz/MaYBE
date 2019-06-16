package com.maybe.maybe.service;

import com.maybe.maybe.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {
    private OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }
}
