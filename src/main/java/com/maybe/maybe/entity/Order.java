package com.maybe.maybe.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order")
public class Order extends AbstractEntity {
    @NotNull
    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @NotNull
    @Column(name = "date_closed", nullable = false)
    private LocalDateTime dateClosed;

    @NotNull
    @Column(name = "total", nullable = false, scale = 12, precision = 2)
    private BigDecimal total;

    @NotNull
    @OneToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @NotNull
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;


    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
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

    public LocalDateTime getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(LocalDateTime dateClosed) {
        this.dateClosed = dateClosed;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
