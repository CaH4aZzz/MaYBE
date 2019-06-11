package com.maybe.maybe.entity;

import com.maybe.maybe.entity.enums.Measure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Component extends AbstractEntity {
    @NotNull
    @Column(name = "component_name")
    private String componentName;
    @NotNull
    private Measure measure;
    @NotNull
    private Double quantity;
    @NotNull
    private BigDecimal price;

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
