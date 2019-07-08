package com.maybe.maybe.service;

import com.maybe.maybe.dto.InvoiceItemDTO;
import com.maybe.maybe.entity.InvoiceItem;
import com.maybe.maybe.repository.InvoiceItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class InvoiceItemService {
    private InvoiceItemRepository invoiceItemRepository;
    private ComponentService componentService;
    private InvoiceService invoiceService;

    public InvoiceItemService(InvoiceItemRepository invoiceItemRepository,
                              ComponentService componentService,
                              InvoiceService invoiceService) {
        this.invoiceItemRepository = invoiceItemRepository;
        this.componentService = componentService;
        this.invoiceService = invoiceService;
    }

    public InvoiceItem findById(Long id) {
        return invoiceItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find invoice item id=" + id));
    }

    public void delete(InvoiceItem invoiceItem) {
        invoiceService.validateUnmodifiedInvoice(invoiceItem.getInvoice());
        componentService.decreaseComponentBalance(invoiceItem.getComponent().getId(),
                invoiceItem.getQuantity());
        invoiceItemRepository.delete(invoiceItem);
    }

    public Page<InvoiceItem> findAllByInvoice_Id(Long invoiceId, Pageable pageable) {
        return invoiceItemRepository.findAllByInvoice_Id(invoiceId, pageable);
    }

    public InvoiceItem createFromDTO(InvoiceItemDTO invoiceItemDTO, Long invoiceId) {
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoice(invoiceService.findById(invoiceId));
        return updateFromDTO(invoiceItem, invoiceItemDTO);
    }

    public InvoiceItem updateFromDTO(InvoiceItem invoiceItem, InvoiceItemDTO invoiceItemDTO) {
        invoiceService.validateUnmodifiedInvoice(invoiceItem.getInvoice());
        if (invoiceItem.getId() != null) {
            componentService.decreaseComponentBalance(invoiceItem.getComponent().getId(),
                    invoiceItem.getQuantity());
        }
        invoiceItem.setPrice(invoiceItemDTO.getPrice());
        invoiceItem.setQuantity(invoiceItemDTO.getQuantity());
        invoiceItem.setComponent(componentService.findById(invoiceItemDTO.getComponentId()));
        componentService.increaseComponentBalance(invoiceItem.getComponent().getId(),
                invoiceItem.getQuantity(),
                invoiceItem.getPrice().multiply(invoiceItem.getQuantity()));
        return invoiceItemRepository.save(invoiceItem);
    }
}
