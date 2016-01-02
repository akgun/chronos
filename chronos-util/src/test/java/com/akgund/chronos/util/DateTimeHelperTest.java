package com.akgund.chronos.util;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class DateTimeHelperTest {
    private DateTimeHelper dateTimeHelper;

    @Before
    public void setUp() throws Exception {
        dateTimeHelper = DateTimeHelper.getInstance();
    }

    @Test
    public void whenPrintDay() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime dateTime1 = formatter.parseDateTime("2015-03-02");
        DateTime dateTime2 = formatter.parseDateTime("2015-03-04");
        String output = dateTimeHelper.printDuration(new Period(dateTime1, dateTime2));
        assertEquals("2 days", output);
    }

    @Test
    public void whenPrintHour() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm:ss");
        DateTime dateTime1 = formatter.parseDateTime("14:02:15");
        DateTime dateTime2 = formatter.parseDateTime("18:03:22");
        String output = dateTimeHelper.printDuration(new Period(dateTime1, dateTime2));
        assertEquals("4 hours 1 minutes 7 seconds", output);
    }

    @Test
    public void whenTestParse() {
        final DateTime dateTime = dateTimeHelper.parseDate("05/12 10:50");
        assertEquals(5, dateTime.getDayOfMonth());
        assertEquals(12, dateTime.getMonthOfYear());
        assertEquals(DateTime.now().getYear(), dateTime.getYear());
        assertEquals(10, dateTime.getHourOfDay());
        assertEquals(50, dateTime.getMinuteOfHour());
    }

    @Test
    public void testPrintDuration() throws Exception {
    }

    @Test
    public void whenPrintDate() throws Exception {
        final DateTime dateTime = DateTime.now()
                .withMonthOfYear(12).withDayOfMonth(20)
                .withHourOfDay(15).withMinuteOfHour(45);

        assertThat(dateTimeHelper.printDate(dateTime), equalTo("20/12 15:45"));
    }

    @Test
    public void whenGetDiffFor1MinThen1Min() throws Exception {
        final DateTime now = DateTime.now();
        final DateTime dateTime1 = now.withMinuteOfHour(1);
        final DateTime dateTime2 = now.withMinuteOfHour(2);

        assertThat(dateTimeHelper.getDiff(dateTime1, dateTime2).getMinutes(), equalTo(1));
    }
}
