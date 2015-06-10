package com.akgund.chronos.gui.event;

import org.joda.time.DateTime;

@FunctionalInterface
public interface IDateTimeSelectionChange {

    void onDateTimeChange(DateTime dateTime);
}
