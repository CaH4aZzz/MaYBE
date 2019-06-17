package com.maybe.maybe.entity.enums.converter;

import com.maybe.maybe.entity.enums.Measure;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MeasureConverter implements AttributeConverter<Measure, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Measure measure) {
        if (measure == null) {
            return null;
        }
        return measure.getId();
    }

    @Override
    public Measure convertToEntityAttribute(Integer id) {
        return Measure.getById(id);
    }
}

