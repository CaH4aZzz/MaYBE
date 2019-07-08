package com.maybe.maybe.service;

import com.maybe.maybe.dto.*;
import com.maybe.maybe.repository.InvoiceItemRepository;
import com.maybe.maybe.repository.OrderItemRepository;
import com.maybe.maybe.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
public class StatisticsService {

    private final static int FIRST_DAY = 1;

    private OrderRepository orderRepository;
    private InvoiceItemRepository invoiceItemRepository;
    private OrderItemRepository orderItemRepository;


    public StatisticsService(OrderRepository orderRepository, InvoiceItemRepository invoiceItemRepository,
                             OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.invoiceItemRepository = invoiceItemRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public List<SummaryDTO> getSummaryReport(LocalDate dateFrom, LocalDate dateTo) {
        return orderRepository.getSummaryReport(getDateFrom(dateFrom), getDateTo(dateTo));
    }

    public List<ComponentReportDTO> getComponentReport(LocalDate dateFrom, LocalDate dateTo) {
        return invoiceItemRepository.getComponentReport(getDateFrom(dateFrom), getDateTo(dateTo));
    }

    public List<ProductReportDTO> getProductReport(LocalDate dateFrom, LocalDate dateTo) {

        return orderItemRepository.getProductReport(getDateFrom(dateFrom), getDateTo(dateTo));
    }

    public List<EmployeeReportDTO> getEmployeeReport(LocalDate dateFrom, LocalDate dateTo) {
        return orderRepository.getEmployeeReport(getDateFrom(dateFrom), getDateTo(dateTo));
    }

    public List<DeskReportDTO> getDeskReport(LocalDate dateFrom, LocalDate dateTo) {
        return orderRepository.getDeskReport(getDateFrom(dateFrom), getDateTo(dateTo));
    }

    private LocalDateTime getDateFrom(LocalDate date) {
        if (Objects.isNull(date)) {
            return LocalDateTime.of(LocalDate.now().withDayOfMonth(FIRST_DAY), LocalTime.MIN);
        } else return LocalDateTime.of(date, LocalTime.MIN);
    }

    private LocalDateTime getDateTo(LocalDate date) {
        if (Objects.isNull(date)) {
            return LocalDateTime.now();
        } else return LocalDateTime.of(date, LocalTime.MAX);
    }
}
