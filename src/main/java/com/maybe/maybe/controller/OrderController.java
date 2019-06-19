package com.maybe.maybe.controller;

import com.maybe.maybe.entity.Order;
import com.maybe.maybe.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.of(Optional.of(orderService.getAll()));
    }

    @GetMapping("/id")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){

        Optional<Order> order = orderService.getById(id);

        if (!order.isPresent()){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok(Optional.of(order));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody Order order) {

        orderService.save(order);

        return ResponseEntity.status(HttpStatus.OK).body(order.getId().toString() + " was created!");
    }

}
