package com.maybe.maybe.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order")
public class Order extends AbstractEntity {
    @NotNull
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;
    @NotNull
    @Column(name = "customer", nullable = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @NotNull
    @Column(name = "employee", nullable = false)
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @NotNull
    @Column(name = "close_date", nullable = false)
    private LocalDateTime closeDate;
    @NotNull
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;
    @NotNull
    @Column(name = "invoice", nullable = false)
    @JoinColumn(name = "invoice_id")
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
