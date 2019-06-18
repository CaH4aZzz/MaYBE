package com.maybe.maybe.entity.enums;

public enum Measure implements EnumDB {
    ;

    private Integer id;
    private String caption;

    Measure(Integer id, String caption) {
        this.id = id;
        this.caption = caption;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getCaption() {
        return caption;
    }
}
