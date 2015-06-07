package com.akgund.chronos.util;


import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class DateTimeHelper {

    private static final String DATE_TIME_FORMAT = "dd/MM HH:mm";

    public static String printDuration(Period period) {
        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendDays().appendSuffix(" days ")
                .appendHours().appendSuffix(" hours ")
                .appendMinutes().appendSuffix(" minutes ")
                .appendSeconds().appendSuffix(" seconds ")
                .toFormatter();

        return formatter.print(period).trim();
    }

    public static String printDate(DateTime dateTime) {
        return DateTimeFormat.forPattern(DATE_TIME_FORMAT).print(dateTime);
    }

    public static DateTime parseDate(String dateStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DATE_TIME_FORMAT);

        DateTime dateTime = dateTimeFormatter.parseDateTime(dateStr);
        dateTime = dateTime.withYear(DateTime.now().getYear());

        return dateTime;
    }

    public static Period getDiff(DateTime dateTime1, DateTime dateTime2) {
        return new Period(dateTime1, dateTime2);
    }
}
