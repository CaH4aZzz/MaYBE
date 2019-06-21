package com.maybe.maybe.controller;

import com.maybe.maybe.dto.InvoiceItemDTO;
import com.maybe.maybe.entity.Invoice;
import com.maybe.maybe.entity.InvoiceItem;
import com.maybe.maybe.service.InvoiceItemService;
import com.maybe.maybe.service.InvoiceService;
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

    private InvoiceService invoiceService;
    private InvoiceItemService invoiceItemService;

    public InvoiceItemController(InvoiceService invoiceService,
                                 InvoiceItemService invoiceItemService) {
        this.invoiceService = invoiceService;
        this.invoiceItemService = invoiceItemService;
    }

    @GetMapping("/invoices/{invoiceId}/invoiceItems/")
    public ResponseEntity<Page<InvoiceItem>> getInvoiceItems(
            @PathVariable("invoiceId") @Min(1) Long invoiceId,
            @PageableDefault(page=0, size=10) Pageable pageable) {
        Page<InvoiceItem> invoiceItems = invoiceItemService.findAllByInvoice_Id(invoiceId, pageable);
        return new ResponseEntity<>(invoiceItems, HttpStatus.OK);
    }

    @GetMapping("/invoiceItems/{invoiceItemId}")
    public ResponseEntity<InvoiceItem> getInvoiceItem(
            @PathVariable("invoiceItemId") @Min(1) Long invoiceItemId) {
        InvoiceItem invoiceItem = invoiceItemService.findById(invoiceItemId);
        return new ResponseEntity<>(invoiceItem, HttpStatus.OK);
    }

    @PostMapping("invoices/{invoiceId}/invoiceItems/")
    public ResponseEntity<InvoiceItem> createInvoiceItem(
            @PathVariable("invoiceId") @Min(1) Long invoiceId,
            @Valid @RequestBody InvoiceItemDTO invoiceItemDTO) {
        Invoice invoice = invoiceService.findById(invoiceId);
        InvoiceItem invoiceItem = invoiceItemService.createFromDTO(invoiceItemDTO);
        invoiceItem.setInvoice(invoice);
        invoiceItemService.save(invoiceItem);
        return new ResponseEntity<>(invoiceItem, HttpStatus.CREATED);
    }

    @PutMapping("/invoiceItems/{invoiceItemId}")
    public ResponseEntity<InvoiceItem> updateInvoiceItem(
            @PathVariable("invoiceItemId") @Min(1) Long invoiceItemId,
            @Valid @RequestBody InvoiceItemDTO invoiceItemDTO) {
        InvoiceItem invoiceItem = invoiceItemService.findById(invoiceItemId);
        invoiceItemService.updateFromDTO(invoiceItem, invoiceItemDTO);
        invoiceItemService.save(invoiceItem);
        return new ResponseEntity<>(invoiceItem, HttpStatus.OK);
    }

    @DeleteMapping("/invoiceItems/{invoiceItemId}")
    public ResponseEntity<InvoiceItem> deleteInvoiceItem(
            @PathVariable("invoiceItemId") @Min(1) Long invoiceItemId) {
        InvoiceItem invoiceItem = invoiceItemService.findById(invoiceItemId);
        invoiceItemService.deleteById(invoiceItemId);
        return new ResponseEntity<>(invoiceItem, HttpStatus.NO_CONTENT);
    }
}