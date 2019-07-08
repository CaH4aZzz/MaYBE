package com.maybe.maybe.repository;

import com.maybe.maybe.dto.ComponentReportDTO;
import com.maybe.maybe.entity.InvoiceItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {

    Page<InvoiceItem> findAllByInvoice_Id(Long invoiceId, Pageable pageable);

    @Query(value =
            "SELECT new com.maybe.maybe.dto.ComponentReportDTO(c.name, c.measure," +
                    "SUM(CASE WHEN i.invoiceType = com.maybe.maybe.entity.enums.InvoiceType.INCOME THEN ii.quantity ELSE 0 END)," +
                    "SUM(CASE WHEN i.invoiceType = com.maybe.maybe.entity.enums.InvoiceType.INCOME THEN (ii.price * ii.quantity) ELSE 0 END)," +
                    "SUM(CASE WHEN i.invoiceType = com.maybe.maybe.entity.enums.InvoiceType.ORDER THEN ii.quantity ELSE 0 END)," +
                    "SUM(CASE WHEN i.invoiceType = com.maybe.maybe.entity.enums.InvoiceType.ORDER THEN (ii.price * ii.quantity) ELSE 0 END))" +
                    "FROM InvoiceItem ii " +
                    "JOIN ii.invoice i " +
                    "JOIN ii.component c " +
                    "WHERE i.dateCreated BETWEEN :dateFrom AND :dateTo " +
                    "GROUP BY c.id, c.measure ORDER BY c.name")
    List<ComponentReportDTO> getComponentReport(@Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);

}
