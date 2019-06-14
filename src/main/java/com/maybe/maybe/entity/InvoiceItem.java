package com.maybe.maybe.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "invoice_item")
public class InvoiceItem extends AbstractEntity {
    @NotNull
    @Column(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "component_id", nullable = false)
    private Component component;

    @NotNull
    @Column(name = "quantity", nullable = false, scale = 15, precision = 3)
    private BigDecimal quantity;

    @NotNull
    @Column(name = "price", nullable = false, scale = 12, precision = 2)
    private BigDecimal price;

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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
