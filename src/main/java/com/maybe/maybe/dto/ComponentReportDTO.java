package com.maybe.maybe.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ComponentReportDTO {

    @NotNull
    private String componentName;

    @NotNull
    private BigDecimal income;

    @NotNull
    private BigDecimal incomeTotal;

    @NotNull
    private BigDecimal outcome;

    @NotNull
    private BigDecimal outcomeTotal;

    public ComponentReportDTO() {
    }

    public ComponentReportDTO(@NotNull String componentName, @NotNull BigDecimal income, @NotNull BigDecimal incomeTotal,
                              @NotNull BigDecimal outcome, @NotNull BigDecimal outcomeTotal) {
        this.componentName = componentName;
        this.income = income;
        this.incomeTotal = incomeTotal;
        this.outcome = outcome;
        this.outcomeTotal = outcomeTotal;
    }

    public String getComponentName() {
        return componentName;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public BigDecimal getIncomeTotal() {
        return incomeTotal;
    }

    public BigDecimal getOutcome() {
        return outcome;
    }

    public BigDecimal getOutcomeTotal() {
        return outcomeTotal;
    }

    @Override
    public String toString() {
        return "ComponentReportDTO{" +
                "componentName='" + componentName + '\'' +
                ", income=" + income +
                ", incomeTotal=" + incomeTotal +
                ", outcome=" + outcome +
                ", outcomeTotal=" + outcomeTotal +
                '}';
    }
}
