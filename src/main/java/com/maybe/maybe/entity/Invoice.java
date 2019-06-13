package com.maybe.maybe.entity;

import com.maybe.maybe.entity.enums.InvoiceType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoice")
public class Invoice extends AbstractNameEntity {
    @NotNull
    @Column(name = "invoice_type_id", nullable = false)
    private InvoiceType invoiceType;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "employee_id",nullable = false)
    private Employee employee;

    public Invoice() {
    }

    public Invoice(InvoiceType invoiceType, String name, LocalDateTime createDate, Employee employee) {
        this.invoiceType = invoiceType;
        this.setName(name);
        this.createDate = createDate;
        this.employee = employee;
    }

    public InvoiceType getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
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
