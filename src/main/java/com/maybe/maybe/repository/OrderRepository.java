package com.maybe.maybe.repository;

import com.maybe.maybe.dto.SummaryDTO;
import com.maybe.maybe.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order getOrderById(Long id);

    @Query(value =
            "SELECT new com.maybe.maybe.dto.SummaryDTO(i.dateCreated, SUM(o.total), SUM(it.price * it.quantity))\n" +
            "FROM Order o, Invoice i, InvoiceItem it\n" +
            "WHERE o.invoice = i\n" +
                "AND it.invoice = i\n" +
                "AND o.dateCreated BETWEEN :dateAt AND :dateTo\n" +
            "GROUP BY(i.dateCreated)")
    List<SummaryDTO> getSummaryReport(@Param("dateAt") LocalDateTime dateAt, @Param("dateTo") LocalDateTime dateTo);
}