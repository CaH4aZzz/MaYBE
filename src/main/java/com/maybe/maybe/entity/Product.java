package com.maybe.maybe.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
@Entity
public class Product extends AbstractEntity {
    @NotNull
    @Column(name = "product")
    private String productName;
    @NotNull
    private BigDecimal price;
}
