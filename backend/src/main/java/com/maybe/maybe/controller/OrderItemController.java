package com.maybe.maybe.controller;


import com.maybe.maybe.annotation.Statistic;
import com.maybe.maybe.dto.OrderItemDTO;
import com.maybe.maybe.entity.OrderItem;
import com.maybe.maybe.service.OrderItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api")
public class OrderItemController {

    private OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @Statistic
    @PostMapping("/orders/{orderId}/orderItems")
    public ResponseEntity<OrderItemDTO> createOrderItemByOrderId(
            @PathVariable("orderId") @Min(1) Long orderId,
            @Valid @RequestBody OrderItemDTO orderItemDTOReq
    ) {
        OrderItem orderItem = orderItemService.createFromDTO(orderItemDTOReq, orderId);

        OrderItemDTO orderItemDTOResp = orderItemService.getOrderItemDTOResp(orderItem);

        return new ResponseEntity<>(orderItemDTOResp, HttpStatus.CREATED);
    }

    @Statistic
    @GetMapping("/orders/{orderId}/orderItems")
    public ResponseEntity<Page<OrderItemDTO>> getOrderItemsByOrderId(
            @PathVariable("orderId") @Min(1) Long orderId,
            @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable
    ) {

        Page<OrderItem> orderItems = orderItemService.findAllByOrderId(orderId, pageable);

        Page<OrderItemDTO> orderItemDTOPage = orderItemService.getOrderItemDTOPage(orderItems);

        return new ResponseEntity<>(orderItemDTOPage, HttpStatus.OK);
    }

    @Statistic
    @GetMapping("/orderItems/{orderItemId}")
    public ResponseEntity<OrderItemDTO> getOrderItemById(
            @PathVariable("orderItemId") @Min(1) Long orderItemId
    ) {
        OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);

        OrderItemDTO orderItemDTOResp = orderItemService.getOrderItemDTOResp(orderItem);

        return new ResponseEntity<>(orderItemDTOResp, HttpStatus.OK);
    }

    @Statistic
    @DeleteMapping("/orderItems/{orderItemId}")
    public ResponseEntity<OrderItemDTO> deleteOrderItem(
            @PathVariable("orderItemId") @Min(1) Long orderItemId) {
        OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);
        OrderItemDTO orderItemDTOResp = orderItemService.getOrderItemDTOResp(orderItem);
        orderItemService.deleteOrderItemById(orderItemId);
        return new ResponseEntity<>(orderItemDTOResp, HttpStatus.OK);
    }

    @Statistic
    @PutMapping("/orderItems/{orderItemId}")
    public ResponseEntity<OrderItemDTO> updateOrderItem(
            @PathVariable("orderItemId") @Min(1) Long orderItemId,
            @Valid @RequestBody OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);
        OrderItem orderItemUpdated = orderItemService.updateFromDTO(orderItem, orderItemDTO);
        OrderItemDTO orderItemDTOResp = orderItemService.getOrderItemDTOResp(orderItemUpdated);
        return new ResponseEntity<>(orderItemDTOResp, HttpStatus.OK);
    }
}
