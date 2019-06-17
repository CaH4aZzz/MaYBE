package com.maybe.maybe.entity.enums;

import java.util.Arrays;

public enum InvoiceType {
    ;

    private Integer id;
    private String caption;

    InvoiceType(Integer id, String caption) {
        this.id = id;
        this.caption = caption;
    }

    public Integer getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }

    public static InvoiceType getById(Integer id) {
        return Arrays.stream(InvoiceType.values())
                .filter(a -> a.getId().equals(id)).findFirst().
                        orElse(null);
    }
}
