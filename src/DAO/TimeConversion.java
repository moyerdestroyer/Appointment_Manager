package DAO;

import Model.Zone;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * This Class handles all time conversions, also converting from local time to string or vice versa
 */
public class TimeConversion {
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * @param timeToConvert Takes a time, and returns it as a string based on the Pattern
     * @return
     */
    public static String dateToString (LocalDateTime timeToConvert) {
        return timeToConvert.format(formatter);
    }

    /**
     * @param timeToConvert An overloaded function, designed to accomodate a zone to convert to
     * @param zone The enum Zone in this param is what you want to convert TO. (if Zone.LOCAL, converts to UTC)
     * @return
     */
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

    /**
     * @param dateToConvert Converts a String to Date
     * @return
     */
    public static LocalDateTime stringToDate (String dateToConvert) {
        return LocalDateTime.parse(dateToConvert, formatter);
    }

    /**
     * @param dateToConvert Converts a String to a date
     * @param zone Overlaod with the zone to go to UTC, or to Local
     * @return
     */
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

    /**
     * @return returns current time in UTC
     */
    public static LocalDateTime now () {
        //GETS the current UTC time
        OffsetDateTime utcNow = OffsetDateTime.now(ZoneOffset.UTC);
        return utcNow.toLocalDateTime();
    }
}
