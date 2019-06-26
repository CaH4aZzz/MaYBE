package com.maybe.maybe.service;

import com.maybe.maybe.dto.OrderItemDTO;
import com.maybe.maybe.entity.Order;
import com.maybe.maybe.entity.OrderItem;
import com.maybe.maybe.entity.Product;
import com.maybe.maybe.repository.OrderItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@Service
public class OrderItemService {
    private OrderItemRepository orderItemRepository;
    private OrderService orderService;
    private ProductService productService;

    public OrderItemService(
            OrderItemRepository orderItemRepository,
            OrderService orderService,
            ProductService productService
    ) {
        this.orderItemRepository = orderItemRepository;
        this.orderService = orderService;
        this.productService = productService;
    }

    public Page<OrderItem> findAllByOrderId(Long orderId, Pageable pageable) {
        return orderItemRepository.findAllByOrderId(orderId, pageable);
    }

    public OrderItem getOrderItemById(Long id) {
        OrderItem orderItem = orderItemRepository.getOrderItemById(id);
        if (orderItem == null) {
            throw new EntityNotFoundException("Order item with id: " + id + " not found");
        } else {
            return orderItem;
        }
    }

    public OrderItem createFromDTO(OrderItemDTO orderItemDTO, Long orderId) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(orderService.getOrderById(orderId));

        return getOrderItemAndUpdateOrderTotal(orderItem, orderItemDTO);
    }

    public OrderItem updateFromDTO(OrderItem orderItem, OrderItemDTO orderItemDTO) {

        orderItem.setOrder(orderService.getOrderById(orderItemDTO.getOrderId()));
        return getOrderItemAndUpdateOrderTotal(orderItem, orderItemDTO);
    }

    private OrderItem getOrderItemAndUpdateOrderTotal(OrderItem orderItem, OrderItemDTO orderItemDTO) {

        Product product = productService.findById(orderItemDTO.getProductId());

        orderItem.setProduct(product);

        orderItem.setQuantity(orderItemDTO.getQuantity());

        BigDecimal price = product.getPrice().multiply(orderItemDTO.getQuantity());

        orderItem.setPrice(price);

        Order order = orderItem.getOrder();

        BigDecimal total = order.getTotal().add(price);
        order.setTotal(total);

        orderService.save(order);

        return orderItemRepository.save(orderItem);
    }

    public OrderItemDTO getOrderItemDTOResp(OrderItem orderItem) {

        OrderItemDTO orderItemDTO = new OrderItemDTO();

        orderItemDTO.setOrderId(orderItem.getOrder().getId());
        orderItemDTO.setProductId(orderItem.getProduct().getId());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        orderItemDTO.setPrice(orderItem.getPrice());

        return orderItemDTO;

    }

    public Page<OrderItemDTO> getOrderItemDTOPage(Page<OrderItem> orderItemPage) {

        List<OrderItemDTO> orderItemDTOSList = new ArrayList<>();

        List<OrderItem> orderItemList = orderItemPage.getContent();

        IntStream.range(0, orderItemList.size()).forEachOrdered(i -> {
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setOrderId(orderItemList.get(i).getOrder().getId());
            orderItemDTO.setProductId(orderItemList.get(i).getProduct().getId());
            orderItemDTO.setQuantity(orderItemList.get(i).getQuantity());
            orderItemDTO.setPrice(orderItemList.get(i).getPrice());
            orderItemDTOSList.add(orderItemDTO);
        });

        return new PageImpl<>(orderItemDTOSList);
    }

    public void deleteItemOrderById(Long id) {
        orderItemRepository.deleteById(id);
    }

    public Set<OrderItemDTO> getOrderItemDTOSet(Order order) {
        Set<OrderItemDTO> orderItemDTOSet = new HashSet<>();
        order.getOrderItems()
                .forEach(orderItem -> orderItemDTOSet.add(getOrderItemDTOResp(orderItem)));
        return orderItemDTOSet;
    }

}