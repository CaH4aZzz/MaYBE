package com.maybe.maybe.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class InvoiceDTO {
    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @NotNull
    @Min(1)
    private Integer invoiceTypeId;

    @NotNull
    @Min(1)
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

    @Override
    public String toString() {
        return "InvoiceDTO{" +
                "name='" + name + '\'' +
                ", invoiceTypeId=" + invoiceTypeId +
                ", employeeId=" + employeeId +
                '}';
    }
}
