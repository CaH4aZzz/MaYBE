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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class OrderItemServiceTest {

    @InjectMocks
    private OrderItemService orderItemService;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderService orderService;

    private Product product;
    private Order order;
    private OrderItem orderItem;
    private OrderItemDTO orderItemDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        product = mock(Product.class);
        when(product.getId()).thenReturn(1L);

        order = mock(Order.class);
        when(order.getId()).thenReturn(1L);

        orderItemDTO = mock(OrderItemDTO.class);

        orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setOrder(order);
        orderItem.setPrice(new BigDecimal(200));
        orderItem.setProduct(product);
        orderItem.setQuantity(new BigDecimal(2));
    }

    @Test
    public void getOrderItemDTORespTest() {
        // when
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

        // when
        when(orderItemRepository.getOrderItemById(orderItemId)).thenReturn(orderItem);
        OrderItem actual = orderItemService.getOrderItemById(orderItemId);

        // then
        assertEquals(orderItem, actual);
    }


}