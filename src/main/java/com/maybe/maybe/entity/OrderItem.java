package com.maybe.maybe.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
public class OrderItem extends AbstractEntity {
    @NotNull
    @Column(name = "order", nullable = false)
    @JoinColumn(name = "product_id")
    private Order order;
    @NotNull
    @Column(name = "product", nullable = false)
    @JoinColumn(name = "product_id")
    private Product product;
    @NotNull
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public OrderItem() {
    }

    public OrderItem(Order order, Product product, BigDecimal price, Integer quantity) {
        this.order = order;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
