package com.maybe.maybe.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class ComponentProduct extends AbstractEntity {
    @NotNull
    private Product product;
    @NotNull
    @OneToMany
    private List<Component> components;
    @NotNull
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
