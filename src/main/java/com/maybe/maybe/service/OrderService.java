package com.maybe.maybe.service;

import com.maybe.maybe.dto.OrderDTO;
import com.maybe.maybe.entity.Order;
import com.maybe.maybe.repository.CustomerRepository;
import com.maybe.maybe.repository.EmployeeRepository;
import com.maybe.maybe.repository.InvoiceRepository;
import com.maybe.maybe.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private EmployeeRepository employeeRepository;
    private InvoiceRepository invoiceRepository;

    public OrderService(
            OrderRepository orderRepository,
            CustomerRepository customerRepository,
            EmployeeRepository employeeRepository,
            InvoiceRepository invoiceRepository
    ) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.invoiceRepository = invoiceRepository;
    }

    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Order getOrderById(Long id) {
        Order order = orderRepository.getOrderById(id);
        if (order == null) {
            throw new EntityNotFoundException("Order with id: " + id + " not found");
        } else {
            return order;
        }
    }

    public OrderDTO getOrderDTOResp(Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setDateCreated(order.getDateCreated());
        orderDTO.setCustomerId(order.getCustomer().getId());
        orderDTO.setEmployeeId(order.getEmployee().getId());
        orderDTO.setDateClosed(order.getDateClosed());
        orderDTO.setTotal(order.getTotal());
        orderDTO.setInvoiceId(order.getInvoice().getId());

        return orderDTO;
    }

    public Order createFromDTO(OrderDTO orderDTO) {

        Order order = new Order();

        order.setDateCreated(orderDTO.getDateCreated());
        order.setCustomer(customerRepository.getOne(orderDTO.getCustomerId()));
        order.setEmployee(employeeRepository.getOne(orderDTO.getEmployeeId()));
        order.setDateClosed(orderDTO.getDateClosed());
        order.setTotal(orderDTO.getTotal());
        order.setInvoice(invoiceRepository.getOne(orderDTO.getInvoiceId()));

        order.setOrderItems(new HashSet<>());

        return orderRepository.save(order);
    }
}
