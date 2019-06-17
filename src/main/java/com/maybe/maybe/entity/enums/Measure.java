package com.maybe.maybe.entity.enums;

import java.util.Arrays;

public enum Measure {
    ;

    private Integer id;
    private String caption;

    Measure(Integer id, String caption) {
        this.id = id;
        this.caption = caption;
    }

    public Integer getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }

    public static Measure getById(Integer id) {
        return Arrays.stream(Measure.values())
                .filter(a -> a.getId().equals(id)).findFirst().
                        orElse(null);
    }
}
