package com.maybe.maybe.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ProductDTO implements Serializable {

    private Long id;
    private String name;
    private BigDecimal price;
    private List<ComponentProductDTO> componentProductDTOs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<ComponentProductDTO> getComponentProductDTOs() {
        return componentProductDTOs;
    }

    public void setComponentProductDTOs(List<ComponentProductDTO> componentProductDTOs) {
        this.componentProductDTOs = componentProductDTOs;
    }
}
