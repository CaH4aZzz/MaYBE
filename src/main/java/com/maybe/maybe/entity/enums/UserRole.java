package com.maybe.maybe.entity.enums;

import java.util.Arrays;

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

    public static UserRole getById(Integer id) {
        return Arrays.stream(UserRole.values())
                .filter(a -> a.getId().equals(id)).findFirst().
                        orElse(null);
    }
}
