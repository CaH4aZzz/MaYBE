package com.maybe.maybe.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductReportDTO {

    @NotNull
    private String productName;

    @NotNull
    @Digits(integer = 15, fraction = 3)
    private BigDecimal quantity;

    @NotNull
    @Digits(integer = 12, fraction = 2)
    private BigDecimal incomeTotal;

//    @NotNull
//    @Digits(integer = 12, fraction = 2)
//    private BigDecimal outcomeTotal;

    public ProductReportDTO() {
    }

    public ProductReportDTO(@NotNull String productName, @NotNull @Digits(integer = 15, fraction = 3) BigDecimal quantity,
                            @NotNull @Digits(integer = 12, fraction = 2) BigDecimal incomeTotal/*,
                            @NotNull @Digits(integer = 12, fraction = 2) BigDecimal outcomeTotal*/) {
        this.productName = productName;
        this.quantity = quantity;
        this.incomeTotal = incomeTotal;
//        this.outcomeTotal = outcomeTotal;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getIncomeTotal() {
        return incomeTotal;
    }

//    public BigDecimal getOutcomeTotal() {
//        return outcomeTotal;
//    }


}
