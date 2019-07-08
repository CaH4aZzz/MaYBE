package com.maybe.maybe.service;

import com.maybe.maybe.dto.OrderDTO;
import com.maybe.maybe.entity.Desk;
import com.maybe.maybe.entity.Employee;
import com.maybe.maybe.entity.Invoice;
import com.maybe.maybe.entity.Order;
import com.maybe.maybe.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    @Spy
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private DeskService deskService;
    @Mock
    private EmployeeService employeeService;
    @Mock
    private InvoiceService invoiceService;
    @Mock
    private SynchronizeInvoiceOrderService synchronizeInvoiceOrderService;

    @Test
    public void findAllTest() {
        //GIVEN
        List<Order> expectOrders = new ArrayList<>();
        expectOrders.add(new Order());
        expectOrders.add(new Order());
        expectOrders.add(new Order());
        Page<Order> expectedPage = new PageImpl<>(expectOrders);
        Pageable pageable = PageRequest.of(1, 10);
        when(orderRepository.findAll(pageable)).thenReturn(expectedPage);

        //WHEN
        Page<Order> page = orderService.findAll(pageable);

        //THEN
        assertEquals(expectedPage, page);
    }

    @Test
    public void getExistentOrderByIdTest() {
        //GIVEN
        Long orderId = 1L;
        Order expectedOrder = new Order();
        expectedOrder.setId(orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(expectedOrder));

        //WHEN
        Order order = orderService.getOrderById(orderId);

        //THEN
        assertEquals(expectedOrder, order);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getNonexistentOrderByIdTest() {
        //GIVEN
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        //WHEN
        orderService.getOrderById(orderId);
    }

    @Test
    public void createFromDTOTest() {
        //GIVEN
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

        when(deskService.findById(deskId)).thenReturn(desk);
        when(employeeService.findById(employeeId)).thenReturn(employee);
        when(invoiceService.createInvoiceForOrder(orderDTO)).thenReturn(invoice);
        when(orderRepository.save(any(Order.class))).thenReturn(expectedOrder);

        ArgumentCaptor<Order> argumentCaptor = ArgumentCaptor.forClass(Order.class);

        //WHEN
        Order order = orderService.createFromDTO(orderDTO);
        verify(orderRepository).save(argumentCaptor.capture());
        Order captorValue = argumentCaptor.getValue();

        //THEN
        assertEquals(expectedOrder, order);
//        assertEquals(orderDTO.getDeskId(), captorValue.getDesk().getId());
        assertEquals(orderDTO.getInvoiceId(), captorValue.getInvoice().getId());
        assertEquals(orderDTO.getEmployeeId(), captorValue.getEmployee().getId());
    }

    @Test
    public void updateFromDTOTest() {
        //GIVEN
        Long deskId = 1L;
        Desk desk = new Desk();
        desk.setId(deskId);

        Long invoiceId = 1L;
        Invoice invoice = new Invoice();
        invoice.setId(invoiceId);

        Long employeeId = 1L;
        Employee employee = new Employee();
        employee.setId(employeeId);

        OrderDTO orderDTO = new OrderDTO();
        Long orderId = 1L;
        orderDTO.setId(orderId);
        orderDTO.setDeskId(deskId);
        orderDTO.setInvoiceId(invoiceId);
        orderDTO.setEmployeeId(employeeId);
        orderDTO.setDateCreated(LocalDateTime.now());
        orderDTO.setDateClosed(LocalDateTime.now());
        orderDTO.setTotal(new BigDecimal(100));

        Order expectedOrder = new Order();
        expectedOrder.setId(orderDTO.getId());

        when(orderRepository.getOne(orderId)).thenReturn(expectedOrder);
        when(deskService.findById(deskId)).thenReturn(desk);
        when(employeeService.findById(employeeId)).thenReturn(employee);
//        when(invoiceService.findById(invoiceId)).thenReturn(invoice);
        when(orderRepository.save(any(Order.class))).thenReturn(expectedOrder);

        ArgumentCaptor<Order> argumentCaptor = ArgumentCaptor.forClass(Order.class);

        //WHEN
        Order order = orderService.updateFromDTO(orderDTO, orderId);
        verify(orderRepository).save(argumentCaptor.capture());
        Order captorValue = argumentCaptor.getValue();

        //THEN
        assertEquals(expectedOrder, order);
        assertEquals(orderDTO.getDeskId(), captorValue.getDesk().getId());
//        assertEquals(orderDTO.getInvoiceId(), captorValue.getInvoice().getId());
        assertEquals(orderDTO.getEmployeeId(), captorValue.getEmployee().getId());
//        assertEquals(orderDTO.getDateClosed(), captorValue.getDateClosed());
    }

    @Test
    public void getOrderDTORespTest() {
        //GIVEN
        Long deskId = 1L;
        Desk desk = new Desk();
        desk.setId(deskId);

        Long invoiceId = 1L;
        Invoice invoice = new Invoice();
        invoice.setId(invoiceId);

        Long employeeId = 1L;
        Employee employee = new Employee();
        employee.setId(employeeId);

        Order order = new Order();
        Long orderId = 1L;
        order.setId(orderId);
        order.setDesk(desk);
        order.setInvoice(invoice);
        order.setEmployee(employee);
        order.setDateCreated(LocalDateTime.now());
        order.setDateClosed(LocalDateTime.now());
        order.setTotal(new BigDecimal(100));

        //WHEN
        OrderDTO orderDTO = orderService.getOrderDTOResp(order);

        //THEN
        assertEquals(orderId, orderDTO.getId());
        assertEquals(deskId, orderDTO.getDeskId());
        assertEquals(invoiceId, orderDTO.getInvoiceId());
        assertEquals(employeeId, orderDTO.getEmployeeId());
        assertEquals(order.getDateCreated(), orderDTO.getDateCreated());
        assertEquals(order.getDateClosed(), orderDTO.getDateClosed());
    }

    @Test
    public void saveTest() {
        //GIVEN
        Order order = new Order();
        when(orderRepository.save(order)).thenReturn(order);

        //WHEN
        orderService.save(order);

        //THEN
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void deleteOrderById() {
        //GIVEN
        Order order = new Order();
        order.setId(1L);
        Long orderId = order.getId();
        Mockito.doReturn(order).when(orderService).getOrderById(orderId);

        //WHEN
        orderService.deleteOrderById(orderId);

        //THEN
        verify(orderRepository, times(1)).deleteById(orderId);
    }
}