package com.maybe.maybe.service;

import com.maybe.maybe.dto.OrderDTO;
import com.maybe.maybe.dto.OrderItemDTO;
import com.maybe.maybe.entity.*;
import com.maybe.maybe.repository.InvoiceItemRepository;
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
    private ComponentService componentService;
    private SynchronizeInvoiceOrderService synchronizeInvoiceOrderService;

    public OrderItemService(
            OrderItemRepository orderItemRepository,
            OrderService orderService,
            ProductService productService,
            InvoiceItemRepository invoiceItemRepository,
            ComponentService componentService,
            SynchronizeInvoiceOrderService synchronizeInvoiceOrderService

    ) {
        this.orderItemRepository = orderItemRepository;
        this.orderService = orderService;
        this.productService = productService;
        this.componentService = componentService;
        this.synchronizeInvoiceOrderService = synchronizeInvoiceOrderService;
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
        Order order = orderService.getOrderById(orderId);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        order.getOrderItems().add(orderItem);

        return getOrderItemAndUpdateOrderTotal(orderItem, orderItemDTO);
    }

    public OrderItem updateFromDTO(OrderItem orderItem, OrderItemDTO orderItemDTO) {

        orderItem.setOrder(orderService.getOrderById(orderItemDTO.getOrderId()));
        return updateOrderItemAndUpdateOrderTotal(orderItem, orderItemDTO);
    }

    private OrderItem updateOrderItemAndUpdateOrderTotal(OrderItem orderItem, OrderItemDTO orderItemDTO) {
        Order order = orderItem.getOrder();

        Product productFromOrderItemDTO = productService.findById(orderItemDTO.getProductId());

        BigDecimal tempTotal = order.getTotal().subtract(orderItem.getPrice());

        BigDecimal priceFromOrderItemDTO = productFromOrderItemDTO.getPrice().multiply(orderItemDTO.getQuantity());

        order.setTotal(tempTotal.add(priceFromOrderItemDTO));

        orderService.save(order);

        orderItem.setProduct(productFromOrderItemDTO);
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setPrice(priceFromOrderItemDTO);

        synchronizeInvoiceOrderService.orderChanged(order);
        return orderItemRepository.save(orderItem);

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

        //setComponentProductsIntoInvoiceItem(product.getComponentProducts(), order, orderItem);
        synchronizeInvoiceOrderService.orderChanged(order);

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

    public void deleteOrderItemById(Long id) {

        OrderItem orderItemForDelete = orderItemRepository.getOrderItemById(id);

        Order order = orderService.getOrderById(orderItemForDelete.getOrder().getId());

        BigDecimal newTotal = order.getTotal().subtract(orderItemForDelete.getPrice());

        order.setTotal(newTotal);

//        orderItemForDelete.getProduct().getComponentProducts().stream().
//                forEach(componentProduct ->
//                        componentService.increaseComponentBalance(componentProduct.getComponent().getId(),
//                                orderItemForDelete.getQuantity().multiply(componentProduct.getQuantity()),
//                                componentProduct.getComponent().getTotal()
//                                ));

        //todo delete invoiceItem need here

        orderService.save(order);

        orderItemRepository.delete(orderItemForDelete);

        synchronizeInvoiceOrderService.orderChanged(order);
    }

    public Set<OrderItemDTO> getOrderItemDTOSet(Order order) {
        Set<OrderItemDTO> orderItemDTOSet = new HashSet<>();
        order.getOrderItems()
                .forEach(orderItem -> orderItemDTOSet.add(getOrderItemDTOResp(orderItem)));
        return orderItemDTOSet;
    }

    public Page<OrderDTO> getOrderDTOPage(Page<Order> orderPage) {

        List<Order> orderList = orderPage.getContent();

        List<OrderDTO> orderDTOList = new ArrayList<>();

        IntStream.range(0, orderList.size()).forEach(i -> {
            Order order = orderList.get(i);
            orderDTOList.add(orderService.getOrderDTOResp(order));
            orderDTOList.get(i).setOrderItemDTOS(getOrderItemDTOSet(order));
        });
        return new PageImpl<>(orderDTOList);
    }
}