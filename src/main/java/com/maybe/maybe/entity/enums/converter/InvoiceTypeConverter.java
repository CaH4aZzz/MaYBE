package com.maybe.maybe.entity.enums.converter;

import com.maybe.maybe.entity.enums.InvoiceType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class InvoiceTypeConverter implements AttributeConverter<InvoiceType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(InvoiceType invoiceType) {
        if (invoiceType == null) {
            return null;
        }
        return invoiceType.getId();
    }

    @Override
    public InvoiceType convertToEntityAttribute(Integer id) {
        return InvoiceType.getById(id);
    }
}

