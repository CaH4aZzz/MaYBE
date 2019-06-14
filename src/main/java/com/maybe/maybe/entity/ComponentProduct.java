package com.maybe.maybe.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "component_product")
public class ComponentProduct extends AbstractEntity {
    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "component_id", nullable = false)
    private Component component;

    @NotNull
    @Column(name = "quantity", nullable = false, scale = 12, precision = 2)
    private BigDecimal quantity;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Component getComponents() {
        return component;
    }

    public void setComponents(Component component) {
        this.component = component;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
