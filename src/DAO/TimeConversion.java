package DAO;

import Model.Zone;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class TimeConversion {
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //Uses an enum overload to determine what to convert to

    public static String dateToString (LocalDateTime timeToConvert) {
        return timeToConvert.format(formatter);
    }
    public static String dateToString (LocalDateTime timeToConvert, Zone zone) {
        if (zone == Zone.LOCAL) { //Convert to Local
            ZonedDateTime zonedTime = timeToConvert.atZone(ZoneId.of("UTC"));
            ZonedDateTime convertedTime = zonedTime.withZoneSameInstant(TimeZone.getDefault().toZoneId());
            return convertedTime.format(formatter);
        } else { //Convert to UTC TimeZone.getDefault().toZoneId()
            ZonedDateTime zonedTime = timeToConvert.atZone(TimeZone.getDefault().toZoneId());
            ZonedDateTime convertedTime = zonedTime.withZoneSameInstant(ZoneId.of("UTC"));
            return convertedTime.format(formatter);
        }
    }

    public static LocalDateTime stringToDate (String dateToConvert) {
        return LocalDateTime.parse(dateToConvert, formatter);
    }
    public static LocalDateTime stringToDate (String dateToConvert, Zone zone) {
        if (zone == Zone.LOCAL) {
            LocalDateTime localTime = LocalDateTime.parse(dateToConvert, formatter);
            ZonedDateTime localTimeWithZone = localTime.atZone(ZoneId.of("UTC"));
            ZonedDateTime convertedTime = localTimeWithZone.withZoneSameInstant(TimeZone.getDefault().toZoneId());
            return convertedTime.toLocalDateTime();
        } else {
            LocalDateTime localTime = LocalDateTime.parse(dateToConvert, formatter);
            ZonedDateTime localTimeWithZone = localTime.atZone(TimeZone.getDefault().toZoneId());
            ZonedDateTime convertedTime = localTimeWithZone.withZoneSameInstant(ZoneId.of("UTC"));
            return convertedTime.toLocalDateTime();
        }
    }


    public static LocalDateTime now () {
        //GETS the current UTC time
        OffsetDateTime utcNow = OffsetDateTime.now(ZoneOffset.UTC);
        return utcNow.toLocalDateTime();
    }
}
