package com.maybe.maybe.controller;

import com.maybe.maybe.entity.Order;
import com.maybe.maybe.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(Optional.of(orderService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){

        Optional<Order> order = orderService.getById(id);

        if (!order.isPresent()){
            return new ResponseEntity<Order>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(Optional.of(order));
    }
}
