package com.maybe.maybe.entity.enums;

public enum UserRole {
    ADMIN(1, "Administrator"),
    USER(2, "User");

    private Integer id;
    private String caption;

    UserRole(Integer id, String caption) {
        this.id = id;
        this.caption = caption;
    }

    public Integer getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }
}
