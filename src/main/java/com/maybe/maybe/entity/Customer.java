package com.maybe.maybe.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "customer")
public class Customer extends AbstractEntity {
    @NotNull
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    public Customer() {
    }

    public Customer(String customerName) {
        this.customerName = customerName;
    }

    public String getName() {
        return customerName;
    }

    public void setName(String customerName) {
        this.customerName = customerName;
    }
}
