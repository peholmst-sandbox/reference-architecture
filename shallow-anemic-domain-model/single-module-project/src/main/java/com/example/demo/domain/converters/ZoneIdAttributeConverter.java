package com.example.demo.domain.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.ZoneId;

@Converter
public class ZoneIdAttributeConverter implements AttributeConverter<ZoneId, String> {

    @Override
    public String convertToDatabaseColumn(ZoneId attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public ZoneId convertToEntityAttribute(String dbData) {
        return dbData == null ? null : ZoneId.of(dbData);
    }
}
