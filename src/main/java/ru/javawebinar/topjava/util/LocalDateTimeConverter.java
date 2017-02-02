package ru.javawebinar.topjava.util;

/**
 * Created by admin on 01.02.2017.
 */

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

public final class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    public LocalDateTimeConverter() {}

    @Override
    public LocalDateTime convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(source, ISO_LOCAL_DATE_TIME);
    }
}