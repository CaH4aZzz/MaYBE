package com.maybe.maybe.dto;

public class DeskRequest {

    private String name;

    public DeskRequest() {
    }

    public DeskRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
