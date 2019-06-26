package com.maybe.maybe.entity.enums;

public enum InvoiceType implements EnumDB {
    INCOME(1),
    ORDER(2);

    private Integer id;

    InvoiceType(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
