package com.akgund.chronos.util;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DateTimeHelperTest {

    @Test
    public void testPrintDay() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime dateTime1 = formatter.parseDateTime("2015-03-02");
        DateTime dateTime2 = formatter.parseDateTime("2015-03-04");
        String output = DateTimeHelper.printDuration(new Period(dateTime1, dateTime2));
        assertEquals("2 days", output);
    }

    @Test
    public void testPrintHour() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm:ss");
        DateTime dateTime1 = formatter.parseDateTime("14:02:15");
        DateTime dateTime2 = formatter.parseDateTime("18:03:22");
        String output = DateTimeHelper.printDuration(new Period(dateTime1, dateTime2));
        assertEquals("4 hours 1 minutes 7 seconds", output);
    }
}
