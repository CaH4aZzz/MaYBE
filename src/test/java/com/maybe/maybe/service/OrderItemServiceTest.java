package com.maybe.maybe.service;

import com.maybe.maybe.dto.OrderDTO;
import com.maybe.maybe.dto.OrderItemDTO;
import com.maybe.maybe.entity.*;
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
import java.time.LocalDateTime;

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

    @Mock
    private SynchronizeInvoiceOrderService synchronizeInvoiceOrderService;

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
    public void deleteOrderItemById() {
        long deskId = 1L;
        Desk desk = new Desk();
        desk.setId(deskId);

        long invoiceId = 1L;
        Invoice invoice = new Invoice();
        invoice.setId(invoiceId);

        long employeeId = 1L;
        Employee employee = new Employee();
        employee.setId(employeeId);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setDeskId(deskId);
        orderDTO.setInvoiceId(invoiceId);
        orderDTO.setEmployeeId(employeeId);
        orderDTO.setDateCreated(LocalDateTime.now());
        orderDTO.setDateClosed(LocalDateTime.now());
        orderDTO.setTotal(new BigDecimal(100));

        Order expectedOrder = new Order();
        expectedOrder.setId(orderDTO.getId());
        expectedOrder.setDesk(desk);
        expectedOrder.setInvoice(invoice);
        expectedOrder.setEmployee(employee);
        expectedOrder.setDateCreated(orderDTO.getDateCreated());
        expectedOrder.setDateClosed(orderDTO.getDateCreated());
        expectedOrder.setTotal(orderDTO.getTotal());

        Product product = new Product();
        product.setName("Name");
        product.setPrice(new BigDecimal(100));

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(expectedOrder);
        orderItem.setProduct(product);
        orderItem.setQuantity(new BigDecimal(100));
        orderItem.setPrice(new BigDecimal(100));
        when(orderItemRepository.getOrderItemById(1L)).thenReturn(orderItem);
        when(orderService.getOrderById(1L)).thenReturn(expectedOrder);

        orderItemService.deleteOrderItemById(1L);

        verify(orderItemRepository,times(1)).delete(orderItem);
    }
}