package com.maybe.maybe.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class InvoiceItem extends AbstractEntity {
    @NotNull
    private Invoice invoice;
    @NotNull
    private Component component;
    @NotNull
    private Double quantity;
    @NotNull
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
