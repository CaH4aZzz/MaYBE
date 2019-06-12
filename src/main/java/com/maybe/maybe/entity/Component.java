package com.maybe.maybe.entity;

import com.maybe.maybe.entity.enums.Measure;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "component")
public class Component extends AbstractEntity {
    @NotNull
    @Column(name = "component_name",nullable = false)
    private String componentName;
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "measure", nullable = false)
    private Measure measure;
    @NotNull
    @Column(name = "quantity", nullable = false)
    private Double quantity;
    @NotNull
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @ManyToMany
    private List<ComponentProduct> componentProduct;

    public List<ComponentProduct> getComponentProduct() {
        return componentProduct;
    }

    public void setComponentProduct(List<ComponentProduct> componentProduct) {
        this.componentProduct = componentProduct;
    }

    public Component() {
    }

    public Component(String componentName, Measure measure, Double quantity, BigDecimal price) {
        this.componentName = componentName;
        this.measure = measure;
        this.quantity = quantity;
        this.price = price;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
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
