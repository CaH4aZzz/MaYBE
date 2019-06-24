package com.maybe.maybe.service;

import com.maybe.maybe.dto.ComponentReportDTO;
import com.maybe.maybe.dto.SummaryDTO;
import com.maybe.maybe.entity.Invoice;
import com.maybe.maybe.repository.InvoiceItemRepository;
import com.maybe.maybe.repository.InvoiceRepository;
import com.maybe.maybe.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
public class StatisticsService {

    private static final Long firstId = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(StatisticsService.class);

    private OrderRepository orderRepository;
    private InvoiceRepository invoiceRepository;
    private InvoiceItemRepository invoiceItemRepository;


    public StatisticsService(OrderRepository orderRepository, InvoiceRepository invoiceRepository, InvoiceItemRepository invoiceItemRepository) {
        this.orderRepository = orderRepository;
        this.invoiceRepository = invoiceRepository;
        this.invoiceItemRepository = invoiceItemRepository;
    }

    public List<SummaryDTO> getSummaryReport(LocalDate dateAt, LocalDate dateTo) {
        LOG.debug("getSummaryReport start for dateAt={}, dateTo={}", dateAt, dateTo);
        if (Objects.isNull(dateTo)) {
            LOG.info("getSummaryReport dateTo is null - override by current date!");
            dateTo = LocalDate.now();
        }
        return orderRepository.getSummaryReport(LocalDateTime.of(dateAt, LocalTime.MIDNIGHT), LocalDateTime.of(dateTo, LocalTime.MIDNIGHT));
    }

    public List<ComponentReportDTO> getComponentReport(LocalDate dateFrom, LocalDate dateTo) {
        if (Objects.isNull(dateFrom)) {
            Invoice invoice = invoiceRepository.findById(firstId)
                    .orElseThrow(() -> new EntityNotFoundException("There is no data yet"));
            dateFrom = invoice.getDateCreated().toLocalDate();
        }

        if (Objects.isNull(dateTo)) {
            dateTo = LocalDate.now();
        }

        return invoiceItemRepository.getComponentReport(dateFrom.atStartOfDay(), dateTo.atStartOfDay());
    }
}
