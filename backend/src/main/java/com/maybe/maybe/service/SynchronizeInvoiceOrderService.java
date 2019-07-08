package com.maybe.maybe.service;

import com.maybe.maybe.entity.*;
import com.maybe.maybe.repository.InvoiceItemRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Service
public class SynchronizeInvoiceOrderService {
    private ComponentService componentService;
    private InvoiceItemRepository invoiceItemRepository;

    public SynchronizeInvoiceOrderService(ComponentService componentService,
                                          InvoiceItemRepository invoiceItemRepository) {
        this.componentService = componentService;
        this.invoiceItemRepository = invoiceItemRepository;
    }

//    public void orderChanged(Order order) {
//        Invoice invoice = order.getInvoice();
//        Set<InvoiceItem> invoiceItems = invoice.getInvoiceItems();
//        resetInvoiceItems(invoiceItems);
//        for (OrderItem orderItem : order.getOrderItems()) {
//            BigDecimal productQuantity = orderItem.getQuantity();
//            Product product = orderItem.getProduct();
//            Set<ComponentProduct> componentProducts = product.getComponentProducts();
//            for (ComponentProduct componentProduct : componentProducts) {
//                Component component = componentProduct.getComponent();
//                InvoiceItem invoiceItem;
//                Optional<InvoiceItem> optionalInvoiceItem = invoiceItems.stream()
//                        .filter(i -> i.getComponent().equals(component)).findFirst();
//                if (optionalInvoiceItem.isPresent()) {
//                    invoiceItem = optionalInvoiceItem.get();
//                } else {
//                    invoiceItem = new InvoiceItem();
//                    invoiceItem.setInvoice(invoice);
//                    invoiceItem.setComponent(component);
//                    invoiceItem.setQuantity(BigDecimal.ZERO);
//                    invoiceItem.setPrice(BigDecimal.ZERO);
//                    invoiceItemRepository.save(invoiceItem);
//                    invoiceItems.add(invoiceItem);
////                    invoice.setInvoiceItems(invoiceItems);
//                }
//                invoiceItem.setPrice(component.getAveragePrice());
//                BigDecimal componentQuantity = productQuantity.multiply(componentProduct.getQuantity());
//                invoiceItem.setQuantity(invoiceItem.getQuantity().add(componentQuantity));
//            }
//        }
//        saveInvoiceItems(invoiceItems);
//    }


    public void orderChanged(Order order) {
        Invoice invoice = order.getInvoice();
        Set<InvoiceItem> invoiceItems = invoice.getInvoiceItems();
        resetInvoiceItems(invoiceItems);
        for (OrderItem orderItem : order.getOrderItems()) {
            BigDecimal productQuantity = orderItem.getQuantity();
            Product product = orderItem.getProduct();
            Set<ComponentProduct> componentProducts = product.getComponentProducts();
            for (ComponentProduct componentProduct : componentProducts) {
                Component component = componentProduct.getComponent();
                InvoiceItem invoiceItem = invoiceItems.stream()
                        .filter(i -> i.getComponent().equals(component)).findFirst()
                        .orElseGet(() -> {
                            InvoiceItem newInvoiceItem = new InvoiceItem();
                            newInvoiceItem.setInvoice(invoice);
                            newInvoiceItem.setComponent(component);
                            newInvoiceItem.setQuantity(BigDecimal.ZERO);
                            newInvoiceItem.setPrice(BigDecimal.ZERO);
                            invoiceItemRepository.save(newInvoiceItem);
                            invoiceItems.add(newInvoiceItem);
                            return newInvoiceItem;
                        });
                invoiceItem.setPrice(component.getAveragePrice());
                BigDecimal componentQuantity = productQuantity.multiply(componentProduct.getQuantity());
                invoiceItem.setQuantity(invoiceItem.getQuantity().add(componentQuantity));
            }
        }
        saveInvoiceItems(invoiceItems);
    }

    public void orderDeleted(Order order) {
        Invoice invoice = order.getInvoice();
        Set<InvoiceItem> invoiceItems = invoice.getInvoiceItems();
        resetInvoiceItems(invoiceItems);
    }

    private void resetInvoiceItems(Set<InvoiceItem> invoiceItems) {
        invoiceItems.forEach(i -> {
            componentService.increaseComponentBalance(i.getComponent().getId(),
                    i.getQuantity(),
                    i.getPrice().multiply(i.getQuantity()));
            i.setQuantity(BigDecimal.ZERO);
        });
    }

    private void saveInvoiceItems(Set<InvoiceItem> invoiceItems) {
        invoiceItems.forEach(i -> {
            if (i.getQuantity().compareTo(BigDecimal.ZERO) == 0) {
                invoiceItemRepository.delete(i);
            } else {
                componentService.decreaseComponentBalance(i.getComponent().getId(), i.getQuantity());
                invoiceItemRepository.save(i);
            }
        });
    }
}
