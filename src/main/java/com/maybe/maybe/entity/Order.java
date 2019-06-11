package com.maybe.maybe.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Order extends AbstractEntity {
    @NotNull
    private LocalDateTime createDate;
    @NotNull
    private Customer customer;
    @NotNull
    private Employee employee;
    @NotNull
    private LocalDateTime closeDate;
    @NotNull
    private BigDecimal totalPrice;
    @NotNull
    private Invoice invoice;

    public Order() {
    }

    public Order(LocalDateTime createDate, Customer customer, Employee employee, LocalDateTime closeDate, BigDecimal totalPrice, Invoice invoice) {
        this.createDate = createDate;
        this.customer = customer;
        this.employee = employee;
        this.closeDate = closeDate;
        this.totalPrice = totalPrice;
        this.invoice = invoice;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDateTime getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDateTime closeDate) {
        this.closeDate = closeDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
