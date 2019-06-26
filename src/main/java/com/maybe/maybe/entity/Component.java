package com.maybe.maybe.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.maybe.maybe.entity.enums.Measure;
import com.maybe.maybe.entity.enums.converter.MeasureConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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

    @NotNull
    @Column(name = "quantity", nullable = false, scale = 15, precision = 3)
    private BigDecimal quantity;

    @NotNull
    @Column(name = "price", nullable = false, scale = 12, precision = 2)
    private BigDecimal price;

    @NotNull
    @OneToMany(mappedBy = "component")
    @JsonBackReference
    private Set<ComponentProduct> componentProduct;

    @NotNull
    @OneToMany(mappedBy = "component")
    @JsonBackReference
    private Set<InvoiceItem> invoiceItems;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(Set<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }
}
