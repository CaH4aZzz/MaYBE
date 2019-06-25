package com.maybe.maybe.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product extends AbstractEntity {
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "price", nullable = false, scale = 12, precision = 2)
    private BigDecimal price;

    @NotNull
    @OneToMany(mappedBy = "product")
    @JsonBackReference
    private Set<ComponentProduct> componentProducts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ComponentProduct> getComponentProducts() {
        return componentProducts;
    }

    public void setComponentProducts(Set<ComponentProduct> componentProducts) {
        this.componentProducts = componentProducts;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
