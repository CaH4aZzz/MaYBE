package com.maybe.maybe.entity.enums;

public enum UserRole implements EnumDB {
    ADMIN(1),
    USER(2);

    private Integer id;

    UserRole(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
