package com.maybe.maybe.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ComponentProductDTO {

    @NotNull
    private Long componentId;
    @NotNull
    private BigDecimal quantity;

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
}
