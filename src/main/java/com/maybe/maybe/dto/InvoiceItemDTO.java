package com.maybe.maybe.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class InvoiceItemDTO {
    @NotNull
    private Long invoiceId;

    @NotNull
    private Long componentId;

    @NotNull
    private BigDecimal quantity;

    @NotNull
    private BigDecimal price;

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

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
}
