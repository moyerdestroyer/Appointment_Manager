package DAO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeConversion {
    //Need to convert all conversions to and from UTC
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");



    public static String dateToString (LocalDateTime timeToConvert) {
        return timeToConvert.format(formatter);
    }

    public static LocalDateTime stringToDate (String dateToConvert) {
        return LocalDateTime.parse(dateToConvert, formatter);
    }
}
