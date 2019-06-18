package com.maybe.maybe.entity.enums.converter;

import com.maybe.maybe.entity.enums.Measure;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

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
        return Arrays.stream(Measure.values())
                .filter(a -> a.getId().equals(id)).findFirst().
                        orElse(null);
    }
}

