package com.maybe.maybe.service;

import com.maybe.maybe.repository.InvoiceItemRepository;
import com.maybe.maybe.repository.OrderItemRepository;
import com.maybe.maybe.repository.OrderRepository;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class StatisticsServiceTest {
    @InjectMocks
    private StatisticsService statisticsService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private InvoiceItemRepository invoiceItemRepository;
    @Mock
    private OrderItemRepository orderItemRepository;

    @Test
    @Parameters(method = "parametersToTest")
    public void getSummaryReport(LocalDate dateFrom, LocalDate dateTo) {
        MockitoAnnotations.initMocks(this);

        statisticsService.getSummaryReport(dateFrom, dateTo);

        verify(orderRepository).getSummaryReport(ArgumentMatchers.any(LocalDateTime.class), ArgumentMatchers.any(LocalDateTime.class));
    }

    @Test
    @Parameters(method = "parametersToTest")
    public void getComponentReport(LocalDate dateFrom, LocalDate dateTo) {
        MockitoAnnotations.initMocks(this);

        statisticsService.getComponentReport(dateFrom, dateTo);

        verify(invoiceItemRepository).getComponentReport(ArgumentMatchers.any(LocalDateTime.class), ArgumentMatchers.any(LocalDateTime.class));
    }

    @Test
    @Parameters(method = "parametersToTest")
    public void getProductReport(LocalDate dateFrom, LocalDate dateTo) {
        MockitoAnnotations.initMocks(this);

        statisticsService.getProductReport(dateFrom, dateTo);

        verify(orderItemRepository).getProductReport(ArgumentMatchers.any(LocalDateTime.class), ArgumentMatchers.any(LocalDateTime.class));
    }

    @Test
    @Parameters(method = "parametersToTest")
    public void getEmployeeReport(LocalDate dateFrom, LocalDate dateTo) {
        MockitoAnnotations.initMocks(this);

        statisticsService.getEmployeeReport(dateFrom, dateTo);

        verify(orderRepository).getEmployeeReport(ArgumentMatchers.any(LocalDateTime.class), ArgumentMatchers.any(LocalDateTime.class));
    }

    @Test
    @Parameters(method = "parametersToTest")
    public void getDeskReport(LocalDate dateFrom, LocalDate dateTo) {
        MockitoAnnotations.initMocks(this);

        statisticsService.getDeskReport(dateFrom,dateTo);

        verify(orderRepository).getDeskReport(ArgumentMatchers.any(LocalDateTime.class),
                ArgumentMatchers.any(LocalDateTime.class));
    }

    private Object[] parametersToTest() {
        return new Object[]{
                new Object[]{LocalDate.of(2010, 12, 31)
                        , LocalDate.of(2011, 11, 21)},
                new Object[]{LocalDate.of(2010, 12, 31), null},
                new Object[]{null, LocalDate.of(2011, 11, 21)},
                new Object[]{null, null}
        };
    }
}