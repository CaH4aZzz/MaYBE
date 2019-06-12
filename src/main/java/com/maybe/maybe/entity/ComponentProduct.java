package com.maybe.maybe.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "component_product")
public class ComponentProduct extends AbstractEntity {
    @NotNull
    @Column(name = "product", nullable = false)
    @JoinColumn(name = "product_id")
    private Product product;
    @NotNull
    @ManyToMany(mappedBy = "componentProduct")
    @Column(name = "component", nullable = false)
    private List<Component> components;
    @NotNull
    @Column(name = "quantity", nullable = false)
    private Double quantity;

    public ComponentProduct() {
    }

    public ComponentProduct(Product product, List<Component> components, Double quantity) {
        this.product = product;
        this.components = components;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
