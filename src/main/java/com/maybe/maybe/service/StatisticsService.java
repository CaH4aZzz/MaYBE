package com.maybe.maybe.service;

import com.maybe.maybe.dto.SummaryDTO;
import com.maybe.maybe.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
public class StatisticsService {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticsService.class);

    private OrderRepository orderRepository;

    public StatisticsService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<SummaryDTO> getSummaryReport(LocalDate dateAt, LocalDate dateTo) {
        LOG.debug("getSummaryReport start for dateAt={}, dateTo={}", dateAt, dateTo);
        if (Objects.isNull(dateTo)) {
            LOG.info("getSummaryReport dateTo is null - override by current date!");
            dateTo = LocalDate.now();
        }
        return orderRepository.getSummaryReport(LocalDateTime.of(dateAt, LocalTime.MIDNIGHT), LocalDateTime.of(dateTo, LocalTime.MIDNIGHT));
    }
}
