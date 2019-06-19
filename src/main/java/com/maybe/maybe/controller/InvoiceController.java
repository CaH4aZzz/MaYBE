package com.maybe.maybe.controller;

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

    @GetMapping("/invoices/")
    public ResponseEntity<Page<Invoice>> getInvoices(
            @RequestParam(name = "dateFrom", required = false)
                @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDateTime dateFrom,
            @RequestParam(name = "dateTo", required = false)
                @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDateTime dateTo,
            @PageableDefault(page=0, size=10) Pageable pageable) {

        Page<Invoice> invoices = invoiceService.findAllByPeriod(dateFrom, dateTo, pageable);
        if (invoices.isEmpty()) {
            return new ResponseEntity<>(invoices, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/invoices/{invoiceId}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable("invoiceId") @Min(1) Long invoiceId) {
        Invoice invoice = invoiceService.findById(invoiceId)
                .orElse(null);
//                .orElseThrow(() -> new CustomException("Invoice not found", HttpStatus.NOT_FOUND));
        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }

    @PostMapping("/invoices/")
    public ResponseEntity<Invoice> createInvoice(@Valid @RequestBody InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceService.createFromDTO(invoiceDTO);
        invoiceService.save(invoice);
        return new ResponseEntity<>(invoice, HttpStatus.CREATED);
    }

    @PutMapping("/invoices/{invoiceId}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable("invoiceId") @Min(1) Long invoiceId,
                                                 @Valid @RequestBody InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceService.findById(invoiceId)
                .orElse(null);
//                .orElseThrow(() -> new CustomException("Invoice not found", HttpStatus.NOT_FOUND));
        invoiceService.updateFromDTO(invoice, invoiceDTO);
        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }

    @DeleteMapping("/invoices/{invoiceId}")
    public ResponseEntity<Invoice> deleteInvoice(@PathVariable("invoiceId") @Min(1) Long invoiceId) {
        // TODO
        Invoice invoice = new Invoice();
        return new ResponseEntity<>(invoice, HttpStatus.NO_CONTENT);
    }
}