package com.maybe.maybe.entity.enums;

public enum Measure implements EnumDB {
    PIESES(1)
    ;

    private Integer id;

    Measure(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
