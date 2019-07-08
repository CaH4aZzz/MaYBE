package com.maybe.maybe.service;

import com.maybe.maybe.dto.InvoiceDTO;
import com.maybe.maybe.dto.OrderDTO;
import com.maybe.maybe.entity.Invoice;
import com.maybe.maybe.entity.enums.InvoiceType;
import com.maybe.maybe.entity.enums.converter.InvoiceTypeConverter;
import com.maybe.maybe.exception.UnmodifiedEntityException;
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
    private ComponentService componentService;

    public InvoiceService(InvoiceRepository invoiceRepository,
                          EmployeeService employeeService,
                          ComponentService componentService) {
        this.invoiceRepository = invoiceRepository;
        this.employeeService = employeeService;
        this.componentService = componentService;
    }

    public Invoice findById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find invoice id=" + id));
    }

    public void delete(Invoice invoice) {
        validateUnmodifiedInvoice(invoice);
        invoice.getInvoiceItems().forEach(i ->
                componentService.decreaseComponentBalance(i.getComponent().getId(), i.getQuantity()));
        invoiceRepository.delete(invoice);
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
        validateUnmodifiedInvoice(invoice);
        invoice.setName(invoiceDTO.getName());
        invoice.setEmployee(employeeService.findById(invoiceDTO.getEmployeeId()));
        invoice.setInvoiceType(InvoiceTypeConverter.getById(invoiceDTO.getInvoiceTypeId()));
        return invoiceRepository.save(invoice);
    }

    void validateUnmodifiedInvoice(Invoice invoice) {
        if (InvoiceType.ORDER.equals(invoice.getInvoiceType())) {
            throw new UnmodifiedEntityException("Could not modified ordered invoice id=" +
                    invoice.getId());
        }
    }

    public Invoice createInvoiceForOrder(OrderDTO orderDTO) {
        Invoice invoice = new Invoice();
        invoice.setDateCreated(LocalDateTime.now());
        invoice.setEmployee(employeeService.findById(orderDTO.getEmployeeId()));
        invoice.setInvoiceType(InvoiceType.ORDER);
        invoice.setName("order_invoice_" + orderDTO.getEmployeeId());
        return invoiceRepository.save(invoice);
    }
}
