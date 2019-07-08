package com.maybe.maybe.controller;

import com.maybe.maybe.annotation.Statistic;
import com.maybe.maybe.dto.InvoiceDTO;
import com.maybe.maybe.entity.Invoice;
import com.maybe.maybe.service.InvoiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class InvoiceController {

    private InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Statistic
    @GetMapping("/invoices")
    public Page<Invoice> getInvoices(
            @RequestParam(name = "dateFrom", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime dateFrom,
            @RequestParam(name = "dateTo", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime dateTo,
            @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
        Page<Invoice> invoices = invoiceService.findAllByPeriod(dateFrom, dateTo, pageable);
        return invoices;
    }

    @Statistic
    @GetMapping("/invoices/{invoiceId}")
    public Invoice getInvoice(
            @PathVariable("invoiceId") @Min(1) Long invoiceId) {
        Invoice invoice = invoiceService.findById(invoiceId);
        return invoice;
    }

    @Statistic
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/invoices")
    public Invoice createInvoice(
            @Valid @RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.createFromDTO(invoiceDTO);
    }

    @Statistic
    @PutMapping("/invoices/{invoiceId}")
    public Invoice updateInvoice(
            @PathVariable("invoiceId") @Min(1) Long invoiceId,
            @Valid @RequestBody InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceService.findById(invoiceId);
        invoiceService.updateFromDTO(invoice, invoiceDTO);
        return invoice;
    }

    @Statistic
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/invoices/{invoiceId}")
    public void deleteInvoice(
            @PathVariable("invoiceId") @Min(1) Long invoiceId) {
        Invoice invoice = invoiceService.findById(invoiceId);
        invoiceService.delete(invoice);
//        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }
}