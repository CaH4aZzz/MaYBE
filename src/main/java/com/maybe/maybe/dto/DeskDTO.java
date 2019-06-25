package com.maybe.maybe.dto;

public class DeskDTO {

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
