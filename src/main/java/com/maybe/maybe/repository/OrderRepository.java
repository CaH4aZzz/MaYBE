package com.maybe.maybe.repository;

import com.maybe.maybe.dto.DeskReportDTO;
import com.maybe.maybe.dto.EmployeeReportDTO;
import com.maybe.maybe.dto.SummaryDTO;
import com.maybe.maybe.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value =
            "SELECT new com.maybe.maybe.dto.SummaryDTO(i.dateCreated, SUM(o.total), SUM(it.price * it.quantity))\n" +
                    "FROM Order o, Invoice i, InvoiceItem it\n" +
                    "WHERE o.invoice = i\n" +
                    "AND it.invoice = i\n" +
                    "AND o.dateCreated BETWEEN :dateFrom AND :dateTo\n" +
                    "GROUP BY(i.dateCreated)")
    List<SummaryDTO> getSummaryReport(@Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);

    @Query("SELECT new com.maybe.maybe.dto.EmployeeReportDTO(e.name, COUNT(o.id), SUM(o.total)) " +
            "FROM Order o " +
            "JOIN o.employee e " +
            "WHERE o.dateCreated BETWEEN :dateFrom AND :dateTo " +
            "GROUP BY(e.name)")
    List<EmployeeReportDTO> getEmployeeReport(LocalDateTime dateFrom, LocalDateTime dateTo);

    @Query("select new  com.maybe.maybe.dto.DeskReportDTO(d.name, count(o.id), sum(o.total))\n" +
            "from Order o, Desk d \n" +
            "where o.dateCreated between :dateFrom and :dateTo \n" +
            "group by(d.name)")
    List<DeskReportDTO> getDeskReport(LocalDateTime dateFrom, LocalDateTime dateTo);

}