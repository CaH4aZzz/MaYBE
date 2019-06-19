package com.maybe.maybe.controller;

import com.maybe.maybe.entity.InvoiceItem;
import com.maybe.maybe.service.InvoiceItemService;
import com.maybe.maybe.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/invoices/{invoiceId}")
public class InvoiceItemController {

    private InvoiceService invoiceService;
    private InvoiceItemService invoiceItemService;

    public InvoiceItemController(InvoiceService invoiceService,
                                 InvoiceItemService invoiceItemService) {
        this.invoiceService = invoiceService;
        this.invoiceItemService = invoiceItemService;
    }

    @GetMapping("/invoiceItems/")
    public ResponseEntity<List<InvoiceItem>> getInvoiceItems(@PathVariable("invoiceId") @Min(1) Long invoiceId) {
        // TODO
        List<InvoiceItem> invoiceItems = new ArrayList<>();
        return new ResponseEntity<>(invoiceItems, HttpStatus.OK);
    }

    @GetMapping("/invoiceItems/{invoiceItemId}")
    public ResponseEntity<InvoiceItem> getInvoiceItem(@PathVariable("invoiceId") @Min(1) Long invoiceId,
                                                      @PathVariable("invoiceItemId") @Min(1) Long invoiceItemId) {
        // TODO
        InvoiceItem invoiceItem = new InvoiceItem();
        return new ResponseEntity<>(invoiceItem, HttpStatus.OK);
    }

    @PostMapping("/invoiceItems/")
    public ResponseEntity<InvoiceItem> createInvoiceItem(@PathVariable("invoiceId") @Min(1) Long invoiceId,
                                                         @Valid @RequestBody InvoiceItem invoiceItem) {
        // TODO
        return new ResponseEntity<>(invoiceItem, HttpStatus.CREATED);
    }

    @PutMapping("/invoiceItems/{invoiceItemId}")
    public ResponseEntity<InvoiceItem> updateInvoiceItem(@PathVariable("invoiceId") @Min(1) Long invoiceId,
                                                         @PathVariable("invoiceItemId") @Min(1) Long invoiceItemId,
                                                         @Valid @RequestBody InvoiceItem invoiceItem) {
        // TODO
        return new ResponseEntity<>(invoiceItem, HttpStatus.OK);
    }

    @DeleteMapping("/invoiceItems/{invoiceItemId}")
    public ResponseEntity<InvoiceItem> deleteInvoiceItem(@PathVariable("invoiceId") @Min(1) Long invoiceId,
                                                         @PathVariable("invoiceItemId") @Min(1) Long invoiceItemId) {
        // TODO
        InvoiceItem invoiceItem = new InvoiceItem();
        return new ResponseEntity<>(invoiceItem, HttpStatus.NO_CONTENT);
    }
}