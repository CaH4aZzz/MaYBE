package com.maybe.maybe.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DeskDTO {

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    public DeskDTO() {
    }

    public DeskDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DeskDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
