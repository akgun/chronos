package com.akgund.chronos.core.impl;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class DateTimeParserTest {
    private DateTimeParser dateTimeParser;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        dateTimeParser = new DateTimeParser();
    }

    @Ignore("Fix")
    @Test
    public void whenValidStrThenParsesDateTime() throws Exception {
        final DateTime dateTime = dateTimeParser.parse("2016-08-22T17:15:00.000+03:00");

        assertThat(dateTime.getYear(), equalTo(2016));
        assertThat(dateTime.getMonthOfYear(), equalTo(8));
        assertThat(dateTime.getDayOfMonth(), equalTo(22));
        assertThat(dateTime.getHourOfDay(), equalTo(17));
        assertThat(dateTime.getMinuteOfHour(), equalTo(15));
    }

    @Test
    public void whenNullParamThenParseThrowsException() {
        exception.expect(Exception.class);

        dateTimeParser.parse(null);
    }

    @Ignore("Fix")
    @Test
    public void whenValidDateThenPrintsDateTime() throws Exception {
        final DateTime dateTime = DateTime.now()
                .withYear(2016).withMonthOfYear(8).withDayOfMonth(22)
                .withHourOfDay(17).withMinuteOfHour(15).withSecondOfMinute(0)
                .withMillisOfSecond(0);
        final String dateTimeStr = dateTimeParser.toString(dateTime);

        assertThat("2016-08-22T17:15:00.000+03:00", equalTo(dateTimeStr));
    }

    @Test
    public void whenNullDateTimeThenToStringReturnsNotEmptyStr() throws ChronosCoreException {
        final String dateTimeStr = dateTimeParser.toString(null);

        assertThat(dateTimeStr, notNullValue());
    }
}