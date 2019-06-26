package com.maybe.maybe.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class OrderDTO {

    private LocalDateTime dateCreated;

    @NotNull
    @Min(1)
    private Long deskId;

    @NotNull
    @Min(1)
    private Long employeeId;

    private LocalDateTime dateClosed;

    @DecimalMin(value = "0.0")
    @Digits(integer = 12, fraction = 2)
    private BigDecimal total;

    @NotNull
    @Min(1)
    private Long invoiceId;

    private Set<OrderItemDTO> orderItemDTOS;

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getDeskId() {
        return deskId;
    }

    public void setDeskId(Long deskId) {
        this.deskId = deskId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Set<OrderItemDTO> getOrderItemDTOS() {
        return orderItemDTOS;
    }

    public void setOrderItemDTOS(Set<OrderItemDTO> orderItemDTOS) {
        this.orderItemDTOS = orderItemDTOS;
    }
}
