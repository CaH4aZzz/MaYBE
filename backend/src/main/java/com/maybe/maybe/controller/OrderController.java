package com.maybe.maybe.controller;

import com.maybe.maybe.annotation.Statistic;
import com.maybe.maybe.dto.OrderDTO;
import com.maybe.maybe.entity.Order;
import com.maybe.maybe.service.OrderItemService;
import com.maybe.maybe.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.HashSet;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class OrderController {

    private OrderService orderService;
    private OrderItemService orderItemService;

    public OrderController(
            OrderService orderService,
            OrderItemService orderItemService
    ) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @Statistic
    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {

        Order order = orderService.createFromDTO(orderDTO);

        OrderDTO orderDTOResp = orderService.getOrderDTOResp(order);

        orderDTOResp.setOrderItemDTOS(new HashSet<>());

        return new ResponseEntity<>(orderDTOResp, HttpStatus.CREATED);
    }

    @Statistic
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<OrderDTO> deleteOrder(
            @PathVariable("orderId") @Min(1) Long orderId) {
        Order order = orderService.getOrderById(orderId);
        OrderDTO orderDTOResp = orderService.getOrderDTOResp(order);
        orderService.deleteOrderById(orderId);
        return new ResponseEntity<>(orderDTOResp, HttpStatus.OK);
    }
    @Statistic
    @PutMapping("/orders/{orderId}")
    public ResponseEntity<OrderDTO> updateOrder(
            @PathVariable("orderId") @Min(1) Long orderId,
            @Valid @RequestBody OrderDTO orderDTO
    ){
        Order order = orderService.updateFromDTO(orderDTO, orderId);

        OrderDTO orderDTOResp = orderService.getOrderDTOResp(order);

        return new ResponseEntity<>(orderDTOResp, HttpStatus.OK);
    }

    @Statistic
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(
            @PathVariable("orderId") Long orderId
    ) {

        Order order = orderService.getOrderById(orderId);

        OrderDTO orderDTO = orderService.getOrderDTOResp(order);

        orderDTO.setOrderItemDTOS(orderItemService.getOrderItemDTOSet(order));

        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }

    @Statistic
    @GetMapping("/orders")
    public ResponseEntity<Page<OrderDTO>> getAllOrders(
            @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable
    ) {

        Page<Order> orderPage = orderService.findAll(pageable);

        Page<OrderDTO> orderDTOPage = orderItemService.getOrderDTOPage(orderPage);

        return new ResponseEntity<>(orderDTOPage, HttpStatus.OK);
    }
}
