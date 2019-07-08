package com.maybe.maybe.dto;

import java.math.BigDecimal;

public class DeskReportDTO {

    private String name;
    private Long ordersNumber;
    private BigDecimal revenue;

    public DeskReportDTO() {
    }

    public DeskReportDTO(String name, Long ordersNumber, BigDecimal revenue) {
        this.name = name;
        this.ordersNumber = ordersNumber;
        this.revenue = revenue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOrdersNumber() {
        return ordersNumber;
    }

    public void setOrdersNumber(Long ordersNumber) {
        this.ordersNumber = ordersNumber;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }
}
