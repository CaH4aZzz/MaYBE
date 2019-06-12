package com.maybe.maybe.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
@Entity
@Table(name = "product")
public class Product extends AbstractEntity {
    @NotNull
    @Column(name = "product_name", nullable = false)
    private String productName;
    @NotNull
    @Column(name = "price", nullable = false)
    private BigDecimal price;
}
