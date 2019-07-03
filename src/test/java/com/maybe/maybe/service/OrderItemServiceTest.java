package com.maybe.maybe.service;

import com.maybe.maybe.dto.OrderItemDTO;
import com.maybe.maybe.entity.Order;
import com.maybe.maybe.entity.OrderItem;
import com.maybe.maybe.entity.Product;
import com.maybe.maybe.repository.OrderItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class OrderItemServiceTest {

    @InjectMocks
    private OrderItemService orderItemService;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderService orderService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getOrderItemDTORespTest() {
        // given
        Product product = mock(Product.class);
        Order order = mock(Order.class);
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setOrder(order);
        orderItem.setPrice(new BigDecimal(200));
        orderItem.setProduct(product);
        orderItem.setQuantity(new BigDecimal(2));

        // when
        when(product.getId()).thenReturn(1L);
        when(order.getId()).thenReturn(1L);
        OrderItemDTO orderItemDTO = orderItemService.getOrderItemDTOResp(orderItem);

        // then
        assertEquals(orderItem.getOrder().getId(), orderItemDTO.getOrderId());
        assertEquals(orderItem.getProduct().getId(), orderItemDTO.getProductId());
        assertEquals(orderItem.getQuantity(), orderItemDTO.getQuantity());
        assertEquals(orderItem.getPrice(), orderItemDTO.getPrice());
    }

    @Test(expected = EntityNotFoundException.class)
    public void getOrderItemByIdTest_when_entity_not_exist_then_EntityNotFountException() {
        // given
        long orderItemId = 1L;

        // when
        when(orderItemRepository.getOrderItemById(orderItemId)).thenReturn(null);

        // then
        orderItemService.getOrderItemById(orderItemId);
    }

    @Test
    public void getOrderItemByIdTest_when_entity_exist_then_return_it() {
        // given
        long orderItemId = 1L;
        Product product = mock(Product.class);
        Order order = mock(Order.class);
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemId);
        orderItem.setOrder(order);
        orderItem.setPrice(new BigDecimal(200));
        orderItem.setProduct(product);
        orderItem.setQuantity(new BigDecimal(2));

        // when
        when(orderItemRepository.getOrderItemById(orderItemId)).thenReturn(orderItem);
        OrderItem actual = orderItemService.getOrderItemById(orderItemId);

        // then
        assertEquals(orderItem, actual);
    }


    @Test
    public void deleteOrderItemByIdTest() {
        long orderItemId = 1L;
        OrderItem orderItem = mock(OrderItem.class);
        Order order = mock(Order.class);

        // when
        when(orderItemRepository.getOrderItemById(orderItemId)).thenReturn(orderItem);
        when(orderItem.getOrder()).thenReturn(order);
        when(order.getId()).thenReturn(1L);
        when(orderService.getOrderById(1L)).thenReturn(order);
        when(order.getTotal()).thenReturn(new BigDecimal(100));
        doCallRealMethod().when(order).setTotal(any());
        when(orderItem.getPrice()).thenReturn(new BigDecimal(20));
        doNothing().when(orderService).save(order);
        doNothing().when(orderItemRepository).delete(orderItem);
        orderItemService.deleteOrderItemById(orderItemId);
        when(order.getTotal()).thenCallRealMethod();

        // then
        BigDecimal expected = new BigDecimal(80);
        BigDecimal actual = order.getTotal();
        assertEquals(expected, actual);
    }
}