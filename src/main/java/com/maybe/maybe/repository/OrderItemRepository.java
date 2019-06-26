package com.maybe.maybe.repository;

import com.maybe.maybe.dto.ProductReportDTO;
import com.maybe.maybe.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query(value =
            "SELECT new com.maybe.maybe.dto.ProductReportDTO(p.name," +
                    "oi.quantity," +
                    "SUM (oi.quantity * oi.price))" +
                    "FROM OrderItem oi " +
                    "JOIN oi.product p " +
                    "JOIN oi.order o " +
                    "WHERE o.dateCreated BETWEEN :dateFrom AND :dateTo " +
                    "GROUP BY p.id, oi.quantity " +
                    "ORDER BY p.name")
    List<ProductReportDTO> getProductReport(@Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);
    Page<OrderItem> findAllByOrderId(Long orderId, Pageable pageable);

    OrderItem getOrderItemById(Long id);
}