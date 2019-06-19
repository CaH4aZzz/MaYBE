package com.maybe.maybe.service;

import com.maybe.maybe.dto.InvoiceDTO;
import com.maybe.maybe.entity.Invoice;
import com.maybe.maybe.entity.enums.converter.InvoiceTypeConverter;
import com.maybe.maybe.repository.InvoiceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class InvoiceService {
    private InvoiceRepository invoiceRepository;
    private EmployeeService employeeService;

    public InvoiceService(InvoiceRepository invoiceRepository, EmployeeService employeeService) {
        this.invoiceRepository = invoiceRepository;
        this.employeeService = employeeService;
    }

    public Optional<Invoice> findById(Long id) {
        return invoiceRepository.findById(id);
    }

    public Page<Invoice> findAllByPeriod(LocalDateTime dateFrom, LocalDateTime dateTo, Pageable pageable) {
        if (dateFrom != null && dateTo != null) {
            return invoiceRepository.findAllByDateCreatedIsBetween(dateFrom, dateTo, pageable);
        } else if (dateTo != null) {
            return invoiceRepository.findAllByDateCreatedIsBefore(dateTo, pageable);
        } else if (dateFrom != null) {
            return invoiceRepository.findAllByDateCreatedIsAfter(dateFrom, pageable);
        } else {
            return invoiceRepository.findAll(pageable);
        }
    }

    public Invoice createFromDTO(InvoiceDTO invoiceDTO) {
        Invoice invoice = new Invoice();
        invoice.setDateCreated(LocalDateTime.now());
        return updateFromDTO(invoice, invoiceDTO);
    }

    public Invoice updateFromDTO(Invoice invoice, InvoiceDTO invoiceDTO) {
        invoice.setName(invoiceDTO.getName());
//        invoice.setEmployee(employeeService.findById(invoiceDTO.getEmployeeId()));
//        invoice.setInvoiceType(InvoiceTypeConverter.getById(invoiceDTO.getInvoiceTypeId()));
        return invoice;
    }

    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }
}
