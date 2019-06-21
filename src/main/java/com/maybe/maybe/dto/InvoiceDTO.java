package com.maybe.maybe.dto;

import javax.validation.constraints.NotNull;

public class InvoiceDTO {
    @NotNull
    private String name;

    @NotNull
    private Integer invoiceTypeId;

    @NotNull
    private Long employeeId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getInvoiceTypeId() {
        return invoiceTypeId;
    }

    public void setInvoiceTypeId(Integer invoiceTypeId) {
        this.invoiceTypeId = invoiceTypeId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
