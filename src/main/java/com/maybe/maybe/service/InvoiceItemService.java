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

    public InvoiceItemService(InvoiceItemRepository invoiceItemRepository,
                              ComponentService componentService) {
        this.invoiceItemRepository = invoiceItemRepository;
        this.componentService = componentService;
    }

    public InvoiceItem findById(Long id) {
        return invoiceItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find invoice item id=" + id));
    }

    public void deleteById(Long id) {
        invoiceItemRepository.deleteById(id);
    }

    public void save(InvoiceItem invoiceItem) {
        invoiceItemRepository.save(invoiceItem);
    }

    public InvoiceItem createFromDTO(InvoiceItemDTO invoiceItemDTO) {
        InvoiceItem invoiceItem = new InvoiceItem();
        updateFromDTO(invoiceItem, invoiceItemDTO);
        return invoiceItem;
    }

    public void updateFromDTO(InvoiceItem invoiceItem, InvoiceItemDTO invoiceItemDTO) {
        invoiceItem.setPrice(invoiceItemDTO.getPrice());
        invoiceItem.setQuantity(invoiceItemDTO.getQuantity());
        invoiceItem.setComponent(componentService.findById(invoiceItemDTO.getComponentId()));
    }

    public Page<InvoiceItem> findAllByInvoice_Id(Long invoiceId, Pageable pageable) {
        return invoiceItemRepository.findAllByInvoice_Id(invoiceId, pageable);
    }
}
