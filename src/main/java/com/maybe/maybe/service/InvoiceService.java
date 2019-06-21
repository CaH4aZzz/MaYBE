package com.maybe.maybe.service;

import com.maybe.maybe.dto.InvoiceDTO;
import com.maybe.maybe.entity.Invoice;
import com.maybe.maybe.entity.enums.converter.InvoiceTypeConverter;
import com.maybe.maybe.repository.InvoiceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
public class InvoiceService {
    private InvoiceRepository invoiceRepository;
    private EmployeeService employeeService;

    public InvoiceService(InvoiceRepository invoiceRepository, EmployeeService employeeService) {
        this.invoiceRepository = invoiceRepository;
        this.employeeService = employeeService;
    }

    public Invoice findById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find invoice id=" + id));
    }

    public void deleteById(Long id) {
        invoiceRepository.deleteById(id);
    }

    public void save(Invoice invoice) {
        invoiceRepository.save(invoice);
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
        updateFromDTO(invoice, invoiceDTO);
        return invoice;
    }

    public void updateFromDTO(Invoice invoice, InvoiceDTO invoiceDTO) {
        invoice.setName(invoiceDTO.getName());
        invoice.setEmployee(employeeService.findById(invoiceDTO.getEmployeeId()));
        invoice.setInvoiceType(InvoiceTypeConverter.getById(invoiceDTO.getInvoiceTypeId()));
    }
}
