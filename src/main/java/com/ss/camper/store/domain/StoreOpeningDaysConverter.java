package com.ss.camper.store.domain;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class StoreOpeningDaysConverter implements AttributeConverter<Set<DayOfWeek>, String> {

    @Override
    public String convertToDatabaseColumn(Set<DayOfWeek> attribute) {
        if (attribute == null || attribute.isEmpty()) return null;
        Set<String> stringAttribute = attribute.stream().map(String::valueOf).collect(Collectors.toSet());
        return String.join(",", stringAttribute);
    }

    @Override
    public Set<DayOfWeek> convertToEntityAttribute(String dbData) {
        if (StringUtils.isBlank(dbData)) return null;
        Set<String> stringAttribute = new HashSet<>(Arrays.asList(dbData.split(",")));
        return stringAttribute.stream().map(DayOfWeek::valueOf).collect(Collectors.toSet());
    }

}
