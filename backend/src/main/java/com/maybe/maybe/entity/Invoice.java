package com.maybe.maybe.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.maybe.maybe.entity.enums.InvoiceType;
import com.maybe.maybe.entity.enums.converter.InvoiceTypeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "invoice")
public class Invoice extends AbstractEntity {
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

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
    @JsonManagedReference
    private Employee employee;

    @NotNull
    @OneToMany(mappedBy = "invoice")
    @JsonManagedReference
    private Set<InvoiceItem> invoiceItems = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Set<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(Set<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }
}
