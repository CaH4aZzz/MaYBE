package com.maybe.maybe.controller;

import com.maybe.maybe.dto.OrderDTO;
import com.maybe.maybe.dto.OrderItemDTO;
import com.maybe.maybe.entity.Order;
import com.maybe.maybe.entity.OrderItem;
import com.maybe.maybe.service.OrderItemService;
import com.maybe.maybe.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

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

    @GetMapping("/orders")
    public ResponseEntity<Page<OrderDTO>> getAllOrders(
            @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable
    ) {

        Page<Order> orderPage = orderService.findAll(pageable);

        List<Order> orderList = orderPage.getContent();

        List<OrderDTO> orderDTOList = new ArrayList<>();

        IntStream.range(0, orderList.size()).forEach(i -> {
            Order order = orderList.get(i);
            orderDTOList.add(orderService.getOrderDTOResp(order));
            orderDTOList.get(i).setOrderItemDTOS(getOrderItemDTOSet(order));
        });

        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList);

        return new ResponseEntity<>(orderDTOPage, HttpStatus.OK);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(
            @PathVariable("orderId") Long orderId
    ) {

        Order order = orderService.getOrderById(orderId);

        OrderDTO orderDTO = orderService.getOrderDTOResp(order);

        orderDTO.setOrderItemDTOS(getOrderItemDTOSet(order));

        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {

        Order order = orderService.createFromDTO(orderDTO);




        OrderDTO orderDTOResp = orderService.getOrderDTOResp(order);

        orderDTOResp.setOrderItemDTOS(getOrderItemDTOSet(order));

        return new ResponseEntity<>(orderDTOResp, HttpStatus.CREATED);
    }

    private Set<OrderItemDTO> getOrderItemDTOSet(Order order){
        Set<OrderItemDTO> orderItemDTOSet = new HashSet<>();
        order.getOrderItems()
                .forEach(orderItem -> orderItemDTOSet.add(orderItemService.getOrderItemDTOResp(orderItem)));
        return orderItemDTOSet;
    }

    private Set<OrderItem> getOrderItemSet(OrderDTO orderDTO){
        Set<OrderItem> orderItemSet = new HashSet<>();
        return null;
    }
}
