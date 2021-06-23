package DAO;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class TimeConversion {
    //Need to convert all conversions to and from UTC
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String dateToString (LocalDateTime timeToConvert) {
        ZonedDateTime zonedTime = timeToConvert.atZone(ZoneId.of("UTC"));
        ZonedDateTime convertedTime = zonedTime.withZoneSameInstant(TimeZone.getDefault().toZoneId());
        return convertedTime.format(formatter);
    }

    public static LocalDateTime stringToDate (String dateToConvert) {
        LocalDateTime localTime = LocalDateTime.parse(dateToConvert, formatter);
        ZonedDateTime localTimeWithZone = localTime.atZone(TimeZone.getDefault().toZoneId());
        ZonedDateTime convertedTime = localTimeWithZone.withZoneSameInstant(ZoneId.of("UTC"));
        return convertedTime.toLocalDateTime();
    }

    public static LocalDateTime now () {
        OffsetDateTime utcNow = OffsetDateTime.now(ZoneOffset.UTC);
        return utcNow.toLocalDateTime();
    }
}
