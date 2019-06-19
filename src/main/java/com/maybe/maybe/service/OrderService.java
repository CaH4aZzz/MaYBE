package com.maybe.maybe.service;

import com.maybe.maybe.entity.Order;
import com.maybe.maybe.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public void save(Order order) {
        orderRepository.save(order);
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> getById(Long id){
        return orderRepository.findById(id);
    }
}
