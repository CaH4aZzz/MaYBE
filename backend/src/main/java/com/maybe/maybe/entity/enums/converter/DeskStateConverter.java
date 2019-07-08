package com.maybe.maybe.entity.enums.converter;

import com.maybe.maybe.entity.enums.DeskState;

import javax.persistence.AttributeConverter;

public class DeskStateConverter extends EnumDBConverter<DeskState> implements AttributeConverter<DeskState, Integer> {
    public DeskStateConverter() {
        super(DeskState.class);
    }

    public static DeskState getById(Integer id) {
        return getById(DeskState.class, id);
    }
}
