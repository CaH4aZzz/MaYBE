package com.maybe.maybe.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SummaryDTO {

    private LocalDate date;
    private BigDecimal income;
    private BigDecimal outcome;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
