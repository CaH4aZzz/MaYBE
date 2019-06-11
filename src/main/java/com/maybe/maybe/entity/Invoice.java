package com.maybe.maybe.entity;

import com.maybe.maybe.entity.enums.InvoiceType;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Invoice extends AbstractEntity {
    @NotNull
    private InvoiceType invoiceType;
    @NotNull
    private String invoiceName;
    @NotNull
    private LocalDateTime createDate;
    @NotNull
    private Employee employee;

    public Invoice() {
    }

    public Invoice(InvoiceType invoiceType, String invoiceName, LocalDateTime createDate, Employee employee) {
        this.invoiceType = invoiceType;
        this.invoiceName = invoiceName;
        this.createDate = createDate;
        this.employee = employee;
    }

    public InvoiceType getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
