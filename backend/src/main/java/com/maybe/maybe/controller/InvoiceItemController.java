package com.maybe.maybe.controller;

import com.maybe.maybe.annotation.Statistic;
import com.maybe.maybe.dto.InvoiceItemDTO;
import com.maybe.maybe.entity.InvoiceItem;
import com.maybe.maybe.service.InvoiceItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api")
public class InvoiceItemController {

    private InvoiceItemService invoiceItemService;

    public InvoiceItemController(InvoiceItemService invoiceItemService) {
        this.invoiceItemService = invoiceItemService;
    }

    @Statistic
    @GetMapping("/invoices/{invoiceId}/invoiceItems")
    public ResponseEntity<Page<InvoiceItem>> getInvoiceItems(
            @PathVariable("invoiceId") @Min(1) Long invoiceId,
            @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
        Page<InvoiceItem> invoiceItems = invoiceItemService.findAllByInvoice_Id(invoiceId, pageable);
        return new ResponseEntity<>(invoiceItems, HttpStatus.OK);
    }

    @Statistic
    @GetMapping("/invoiceItems/{invoiceItemId}")
    public ResponseEntity<InvoiceItem> getInvoiceItem(
            @PathVariable("invoiceItemId") @Min(1) Long invoiceItemId) {
        InvoiceItem invoiceItem = invoiceItemService.findById(invoiceItemId);
        return new ResponseEntity<>(invoiceItem, HttpStatus.OK);
    }

    @Statistic
    @PostMapping("invoices/{invoiceId}/invoiceItems")
    public ResponseEntity<InvoiceItem> createInvoiceItem(
            @PathVariable("invoiceId") @Min(1) Long invoiceId,
            @Valid @RequestBody InvoiceItemDTO invoiceItemDTO) {
        InvoiceItem invoiceItem = invoiceItemService.createFromDTO(invoiceItemDTO, invoiceId);
        return new ResponseEntity<>(invoiceItem, HttpStatus.CREATED);
    }

    @Statistic
    @PutMapping("/invoiceItems/{invoiceItemId}")
    public ResponseEntity<InvoiceItem> updateInvoiceItem(
            @PathVariable("invoiceItemId") @Min(1) Long invoiceItemId,
            @Valid @RequestBody InvoiceItemDTO invoiceItemDTO) {
        InvoiceItem invoiceItem = invoiceItemService.findById(invoiceItemId);
        invoiceItemService.updateFromDTO(invoiceItem, invoiceItemDTO);
        return new ResponseEntity<>(invoiceItem, HttpStatus.OK);
    }

    @Statistic
    @DeleteMapping("/invoiceItems/{invoiceItemId}")
    public ResponseEntity<InvoiceItem> deleteInvoiceItem(
            @PathVariable("invoiceItemId") @Min(1) Long invoiceItemId) {
        InvoiceItem invoiceItem = invoiceItemService.findById(invoiceItemId);
        invoiceItemService.delete(invoiceItem);
        return new ResponseEntity<>(invoiceItem, HttpStatus.OK);
    }
}