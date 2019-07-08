package com.maybe.maybe.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class InvoiceItemDTO {
    @NotNull
    @Min(0)
    private Long componentId;

    @NotNull
    @Digits(integer = 15, fraction = 3)
    private BigDecimal quantity;

    @NotNull
    @DecimalMin(value = "0.0")
    @Digits(integer = 12, fraction = 2)
    private BigDecimal price;

    public Long getComponentId() {
        return componentId;
    }

    public void setComponentId(Long componentId) {
        this.componentId = componentId;
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

    @Override
    public String toString() {
        return "InvoiceItemDTO{" +
                "componentId=" + componentId +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
