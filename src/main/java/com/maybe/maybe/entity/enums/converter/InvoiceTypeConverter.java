package com.maybe.maybe.entity.enums.converter;

import com.maybe.maybe.entity.enums.InvoiceType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter
public class InvoiceTypeConverter
        extends EnumDBConverter<InvoiceType>
        implements AttributeConverter<InvoiceType, Integer> {

    public InvoiceTypeConverter() {
        super(InvoiceType.class);
    }

    public static Optional<InvoiceType> getById(Integer id) {
        return getById(InvoiceType.class, id);
    }
}

