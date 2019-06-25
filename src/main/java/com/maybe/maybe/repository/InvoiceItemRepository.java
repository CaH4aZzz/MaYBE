package com.maybe.maybe.repository;

import com.maybe.maybe.entity.InvoiceItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
    Page<InvoiceItem> findAllByInvoice_Id(Long invoiceId, Pageable pageable);
}