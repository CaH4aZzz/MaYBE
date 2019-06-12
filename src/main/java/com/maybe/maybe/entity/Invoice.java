package com.maybe.maybe.entity;

import com.maybe.maybe.entity.enums.InvoiceType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoice")
public class Invoice extends AbstractEntity {
    @NotNull
    @Column(name = "invoice_type", nullable = false)
    private InvoiceType invoiceType;
    @NotNull
    @Column(name = "invoice_name", nullable = false,unique = true)
    private String invoiceName;
    @NotNull
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;
    @NotNull
    @Column(name = "employee_id", nullable = false)
    @JoinColumn(name = "employee_id")
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
