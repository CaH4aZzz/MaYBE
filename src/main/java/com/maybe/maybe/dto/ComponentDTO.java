package com.maybe.maybe.dto;

import com.maybe.maybe.entity.enums.Measure;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class ComponentDTO implements Serializable, Cloneable {

    @NotEmpty
    private String name;
    @NotEmpty
    private Measure measure;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }
}
