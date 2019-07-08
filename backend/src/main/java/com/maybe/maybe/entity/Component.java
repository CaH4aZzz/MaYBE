package com.maybe.maybe.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maybe.maybe.entity.enums.Measure;
import com.maybe.maybe.entity.enums.converter.MeasureConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "component")
public class Component extends AbstractEntity {
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Convert(converter = MeasureConverter.class)
    @Column(name = "measure_id", nullable = false)
    private Measure measure;

    @Column(name = "quantity", nullable = false, scale = 15, precision = 5)
    private BigDecimal quantity;

    @Column(name = "total", nullable = false, scale = 12, precision = 5)
    private BigDecimal total;

    @OneToMany(mappedBy = "component")
    @JsonBackReference
    private Set<ComponentProduct> componentProduct = new HashSet<>();

    @OneToMany(mappedBy = "component")
    @JsonBackReference
    private Set<InvoiceItem> invoiceItems = new HashSet<>();

    public Set<ComponentProduct> getComponentProduct() {
        return componentProduct;
    }

    public void setComponentProduct(Set<ComponentProduct> componentProduct) {
        this.componentProduct = componentProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Set<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(Set<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    @JsonIgnore
    public BigDecimal getAveragePrice() {
        return this.total.divide(quantity, 5, RoundingMode.FLOOR);
    }
}
