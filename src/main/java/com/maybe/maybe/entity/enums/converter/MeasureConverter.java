package com.maybe.maybe.entity.enums.converter;

import com.maybe.maybe.entity.enums.Measure;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter
public class MeasureConverter
        extends EnumDBConverter<Measure>
        implements AttributeConverter<Measure, Integer> {

    public MeasureConverter() {
        super(Measure.class);
    }

    public static Optional<Measure> getById(Integer id) {
        return getById(Measure.class, id);
    }
}

