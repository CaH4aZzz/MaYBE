package com.maybe.maybe.repository;

import com.maybe.maybe.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Page<Invoice> findAllByDateCreatedIsAfter(LocalDateTime dateFrom, Pageable pageable);

    Page<Invoice> findAllByDateCreatedIsBefore(LocalDateTime dateTo, Pageable pageable);

    Page<Invoice> findAllByDateCreatedIsBetween(LocalDateTime dateFrom, LocalDateTime dateTo, Pageable pageable);

    Invoice findFirstBy();
}