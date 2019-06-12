package com.maybe.maybe.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "invoice_item")
public class InvoiceItem extends AbstractEntity {
    @NotNull
    @Column(name = "invoice", nullable = false)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
    @NotNull
    @Column(name = "component", nullable = false)
    @JoinColumn(name = "component_id")
    private Component component;
    @NotNull
    @Column(name = "quantity", nullable = false)
    private Double quantity;
    @NotNull
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public InvoiceItem() {
    }

    public InvoiceItem(Invoice invoice, Component component, Double quantity, BigDecimal price) {
        this.invoice = invoice;
        this.component = component;
        this.quantity = quantity;
        this.price = price;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
