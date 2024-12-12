//package com.churchwebsite.churchwebsite.utils;
//
//import jakarta.persistence.AttributeConverter;
//import jakarta.persistence.Converter;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//@Converter(autoApply = false)
//public class LocalDateFormatConverter implements AttributeConverter<LocalDate, String> {
//
//    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//
//    @Override
//    public String convertToDatabaseColumn(LocalDate attribute) {
//        return (attribute == null ? null : attribute.format(formatter));
//    }
//
//    @Override
//    public LocalDate convertToEntityAttribute(String dbData) {
//        return (dbData == null ? null : LocalDate.parse(dbData, formatter));
//    }
//}
