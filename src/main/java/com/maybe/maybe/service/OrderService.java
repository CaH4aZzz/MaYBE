package com.maybe.maybe.service;

import com.maybe.maybe.dto.OrderDTO;
import com.maybe.maybe.entity.Invoice;
import com.maybe.maybe.entity.Order;
import com.maybe.maybe.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    private DeskService deskService;
    private EmployeeService employeeService;
    private InvoiceService invoiceService;

    public OrderService(
            OrderRepository orderRepository,
            DeskService deskService,
            EmployeeService employeeService,
            InvoiceService invoiceService
    ) {
        this.orderRepository = orderRepository;
        this.deskService = deskService;
        this.employeeService = employeeService;
        this.invoiceService = invoiceService;
    }

    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find order with id = " + id));
    }

    public Order createFromDTO(OrderDTO orderDTO) {

        Order order = new Order();

        Invoice orderInvoice = invoiceService.createInvoiceForOrder(orderDTO);

        order.setDesk(deskService.findById(orderDTO.getDeskId()));
        order.setEmployee(employeeService.findById(orderDTO.getEmployeeId()));

        order.setInvoice(orderInvoice);
        order.setDateCreated(orderInvoice.getDateCreated());

        order.setTotal(new BigDecimal(0));

        return orderRepository.save(order);
    }

    public Order updateFromDTO(OrderDTO orderDTO, Long id){
        Order order = orderRepository.getOne(id);
        order.setDesk(deskService.findById(orderDTO.getDeskId()));
        order.setEmployee(employeeService.findById(orderDTO.getEmployeeId()));
        order.setInvoice(invoiceService.findById(orderDTO.getInvoiceId()));

        if (orderDTO.getDateClosed() != null){
            order.setDateClosed(orderDTO.getDateClosed());
        }
        return orderRepository.save(order);
    }

    public OrderDTO getOrderDTOResp(Order order) {

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setDeskId(order.getDesk().getId());
        orderDTO.setEmployeeId(order.getEmployee().getId());
        orderDTO.setInvoiceId(order.getInvoice().getId());

        orderDTO.setDateCreated(order.getDateCreated());
        orderDTO.setDateClosed(order.getDateClosed());

        orderDTO.setTotal(order.getTotal());

        orderDTO.setId(order.getId());

        return orderDTO;
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public void deleteOrderById(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
