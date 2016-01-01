package com.akgund.chronos.core.impl;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

public class DateTimeParser {

    public DateTime parse(String dateTimeStr) {
        return ISODateTimeFormat.dateTime().parseDateTime(dateTimeStr);
    }

    public String toString(DateTime dateTime) {
        return ISODateTimeFormat.dateTime().print(dateTime);
    }
}
