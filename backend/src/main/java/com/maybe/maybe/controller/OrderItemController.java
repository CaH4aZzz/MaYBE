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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/orders/{orderId}/orderItems")
    public OrderItemDTO createOrderItemByOrderId(
            @PathVariable("orderId") @Min(1) Long orderId,
            @Valid @RequestBody OrderItemDTO orderItemDTOReq
    ) {
        OrderItem orderItem = orderItemService.createFromDTO(orderItemDTOReq, orderId);

        OrderItemDTO orderItemDTOResp = orderItemService.getOrderItemDTOResp(orderItem);

        return orderItemDTOResp;
    }

    @Statistic
    @GetMapping("/orders/{orderId}/orderItems")
    public Page<OrderItemDTO> getOrderItemsByOrderId(
            @PathVariable("orderId") @Min(1) Long orderId,
            @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable
    ) {

        Page<OrderItem> orderItems = orderItemService.findAllByOrderId(orderId, pageable);

        Page<OrderItemDTO> orderItemDTOPage = orderItemService.getOrderItemDTOPage(orderItems);

        return orderItemDTOPage;
    }

    @Statistic
    @GetMapping("/orderItems/{orderItemId}")
    public OrderItemDTO getOrderItemById(
            @PathVariable("orderItemId") @Min(1) Long orderItemId
    ) {
        OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);

        return orderItemService.getOrderItemDTOResp(orderItem);
    }

    @Statistic
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/orderItems/{orderItemId}")
    public void deleteOrderItem(
            @PathVariable("orderItemId") @Min(1) Long orderItemId) {
//        OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);
//        OrderItemDTO orderItemDTOResp = orderItemService.getOrderItemDTOResp(orderItem);
        orderItemService.deleteOrderItemById(orderItemId);
//        return new ResponseEntity<>(orderItemDTOResp, HttpStatus.OK);
    }

    @Statistic
    @PutMapping("/orderItems/{orderItemId}")
    public OrderItemDTO updateOrderItem(
            @PathVariable("orderItemId") @Min(1) Long orderItemId,
            @Valid @RequestBody OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);
        OrderItem orderItemUpdated = orderItemService.updateFromDTO(orderItem, orderItemDTO);
        OrderItemDTO orderItemDTOResp = orderItemService.getOrderItemDTOResp(orderItemUpdated);
        return orderItemDTOResp;
    }
}
