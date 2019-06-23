package com.maybe.maybe.controller;

import com.maybe.maybe.dto.ComponentReportDTO;
import com.maybe.maybe.dto.SummaryDTO;
import com.maybe.maybe.entity.Invoice;
import com.maybe.maybe.repository.InvoiceRepository;
import com.maybe.maybe.service.StatisticsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private StatisticsService statisticsService;
    private InvoiceRepository invoiceRepository;

    public StatisticsController(StatisticsService statisticsService, InvoiceRepository invoiceRepository) {
        this.statisticsService = statisticsService;
        this.invoiceRepository = invoiceRepository;
    }

    @GetMapping("/summary")
    public List<SummaryDTO> getSummaryReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateAt,
            @RequestParam(required = false)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo
    ) {
        return statisticsService.getSummaryReport(dateAt, dateTo);
    }

    @GetMapping("/component-report")
    public Page<Invoice> getComponentReportDTO(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @PageableDefault(size=Integer.MAX_VALUE) Pageable pageable
    ){

        return statisticsService.getComponentReport(dateFrom, dateTo, pageable);
    }
}
