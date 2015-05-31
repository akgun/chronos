package com.akgund.chronos.util;


import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class DateTimeHelper {

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
        return DateTimeFormat.forPattern("dd/MM HH:mm").print(dateTime);
    }

    public static Period getDiff(DateTime dateTime1, DateTime dateTime2) {
        return new Period(dateTime1, dateTime2);
    }
}
