package com.maybe.maybe.entity.enums;

public enum Measure implements EnumDB {
    GRAM(1),
    LITER(2),
    BOTTLE(3),
    EACH(4),
    PIESES(5);

    private Integer id;

    Measure(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
