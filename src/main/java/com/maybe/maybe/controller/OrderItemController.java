package com.maybe.maybe.controller;

import com.maybe.maybe.entity.Order;
import com.maybe.maybe.entity.OrderItem;
import com.maybe.maybe.service.OrderItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/orders/id/items")
public class OrderItemController {

    private OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public ResponseEntity<?> getAllItemsInOrder(@RequestBody Order order) {
        return ResponseEntity.of(Optional.of(orderItemService.getAllInOrder(order)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){

        Optional<OrderItem> orderItem = orderItemService.getById(id);

        if (!orderItem.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(orderItem);
    }
}
