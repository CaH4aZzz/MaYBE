package com.maybe.maybe.entity.enums;

public enum DeskState implements EnumDB {
    AVAILABLE(1),
    NOT_AVAILABLE(2),
    RESERVED(3);

    private Integer id;

    DeskState(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
