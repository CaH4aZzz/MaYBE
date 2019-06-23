package com.maybe.maybe.entity.enums;

public enum Measure implements EnumDB {
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
