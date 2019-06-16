package com.maybe.maybe.service;

import com.maybe.maybe.repository.InvoiceItemRepository;
import org.springframework.stereotype.Service;

@Service
public class InvoiceItemService {
    private InvoiceItemRepository invoiceItemRepository;

    public InvoiceItemService(InvoiceItemRepository invoiceItemRepository) {
        this.invoiceItemRepository = invoiceItemRepository;
    }
}
