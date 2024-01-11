package com.example.demo.ui.converters;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.time.DateTimeException;
import java.time.ZoneId;

public class StringToZoneIdConverter implements Converter<String, ZoneId> {
    @Override
    public Result<ZoneId> convertToModel(String value, ValueContext context) {
        if (value == null || value.isEmpty()) {
            return Result.ok(null);
        } else {
            try {
                return Result.ok(ZoneId.of(value));
            } catch (DateTimeException ex) {
                return Result.error(ex.getMessage());
            }
        }
    }

    @Override
    public String convertToPresentation(ZoneId value, ValueContext context) {
        return value == null ? "" : value.getId();
    }
}
