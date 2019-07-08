package com.maybe.maybe.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SummaryDTO {
    @NotNull
    private LocalDateTime date;

    @NotNull
    private BigDecimal income;

    @NotNull
    private BigDecimal outcome;

    public SummaryDTO() {
    }

    public SummaryDTO(@NotNull LocalDateTime date, @NotNull BigDecimal income, @NotNull BigDecimal outcome) {
        this.date = date;
        this.income = income;
        this.outcome = outcome;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getOutcome() {
        return outcome;
    }

    public void setOutcome(BigDecimal outcome) {
        this.outcome = outcome;
    }

    @Override
    public String toString() {
        return "SummaryDTO{" +
                "date=" + date +
                ", income=" + income +
                ", outcome=" + outcome +
                '}';
    }
}
