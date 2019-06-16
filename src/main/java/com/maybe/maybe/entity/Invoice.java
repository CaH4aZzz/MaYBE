package com.maybe.maybe.entity;

import com.maybe.maybe.entity.enums.InvoiceType;
import com.maybe.maybe.entity.enums.converter.InvoiceTypeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoice")
public class Invoice extends AbstractNameEntity {
    @NotNull
    @Convert(converter = InvoiceTypeConverter.class)
    @Column(name = "invoice_type_id", nullable = false)
    private InvoiceType invoiceType;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public InvoiceType getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
