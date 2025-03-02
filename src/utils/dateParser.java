package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;


public class DateParser {

    public static String dateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);
        return formattedDate;
    }

    public static String dateToFrenchString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = date.format(formatter);
        return formattedDate;
    }

    public static LocalDate stringToDate(String dateString) {
        LocalDate date = LocalDate.parse(dateString);
        return date;
    }

    public static LocalDate intToDate(Integer year, Integer month, Integer day) {
        LocalDate date = LocalDate.of(year, month, day);
        return date;
    }

    public static String intToStingDate(Integer year, Integer month, Integer day) {
        LocalDate date = LocalDate.of(year, month, day);
        return dateToString(date);
    }

    // Date and time
    public static String dateTimeToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedDateTime = dateTime.format(formatter);
        return formattedDateTime;
    }

    public static LocalDateTime stringToDateTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString);
    }
}


