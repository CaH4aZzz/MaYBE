package com.maybe.maybe.service;

import com.maybe.maybe.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    private InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }
}
