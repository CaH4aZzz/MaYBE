package com.maybe.maybe.entity;

import com.maybe.maybe.entity.enums.Measure;
import com.maybe.maybe.entity.enums.converter.MeasureConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "component")
public class Component extends AbstractNameEntity {
    @NotNull
    @Convert(converter = MeasureConverter.class)
    @Column(name = "measure_id", nullable = false)
    private Measure measure;

    @NotNull
    @Column(name = "quantity", nullable = false, scale = 15, precision = 3)
    private Double quantity;

    @NotNull
    @Column(name = "price", nullable = false, scale = 12, precision = 2)
    private BigDecimal price;

    @NotNull
    @OneToMany(mappedBy = "component")
    private List<ComponentProduct> componentProduct;

    @NotNull
    @OneToMany(mappedBy = "component")
    private List<InvoiceItem> invoiceItems;

    public List<ComponentProduct> getComponentProduct() {
        return componentProduct;
    }

    public void setComponentProduct(List<ComponentProduct> componentProduct) {
        this.componentProduct = componentProduct;
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

    public List<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }
}
