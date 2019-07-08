package com.maybe.maybe.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class EmployeeReportDTO {

    private String employeeName;
    private Long orderCount;
    private BigDecimal total;
    private BigDecimal averageBill;

    public EmployeeReportDTO(String employeeName, Long orderCount, BigDecimal total) {
        this.employeeName = employeeName;
        this.orderCount = orderCount;
        this.total = total;
        if (Objects.isNull(orderCount) || orderCount == 0L) {
            averageBill = new BigDecimal(0);
        } else {
            averageBill = total.divide(new BigDecimal(orderCount));
        }
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Long orderCount) {
        this.orderCount = orderCount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getAverageBill() {
        return averageBill;
    }

    public void setAverageBill(BigDecimal averageBill) {
        this.averageBill = averageBill;
    }
}
