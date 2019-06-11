package com.maybe.maybe.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;


@Entity
public class Customer extends AbstractEntity {
    @NotNull
    private String name;

    public Customer() {
    }

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
