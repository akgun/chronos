package com.akgund.chronos.core.impl;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.util.Locale;

public class DateTimeParser {
    private Locale locale = Locale.forLanguageTag("tr");

    public DateTime parse(String dateTimeStr) {
        return ISODateTimeFormat.dateTime().withLocale(locale).parseDateTime(dateTimeStr);
    }

    public String toString(DateTime dateTime) {
        return ISODateTimeFormat.dateTime().withLocale(locale).print(dateTime);
    }
}
