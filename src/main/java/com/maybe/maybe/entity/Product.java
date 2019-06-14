package com.maybe.maybe.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "product")
public class Product extends AbstractNameEntity {
    @NotNull
    @Column(name = "price", nullable = false, scale = 12, precision = 2)
    private BigDecimal price;

    @NotNull
    @OneToMany(mappedBy = "product")
    private List<ComponentProduct> componentProducts;

    public List<ComponentProduct> getComponentProducts() {
        return componentProducts;
    }

    public void setComponentProducts(List<ComponentProduct> componentProducts) {
        this.componentProducts = componentProducts;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
