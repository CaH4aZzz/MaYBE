package com.maybe.maybe.entity.enums;

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
}
