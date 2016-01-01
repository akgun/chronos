package com.akgund.chronos.core.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DateTimeSerializerTest {
    @Mock
    private DateTimeJsonConverter dateTimeJsonConverter;
    @Mock
    private DateTimeParser dateTimeParser;
    @InjectMocks
    private DateTimeSerializer dateTimeSerializer;

    @Before
    public void setup() {
        dateTimeSerializer = new DateTimeSerializer();

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDeserialize() throws Exception {
//        when(dateTimeJsonConverter
//                .deserialize(anyObject())).thenReturn("2016-08-22T17:15:00.000+03:00");
//
//        final DateTime dateTime = dateTimeSerializer.deserialize(null, null, null);
//
//        assertThat(dateTime.getYear(), equalTo(2016));
//        assertThat(dateTime.getMonthOfYear(), equalTo(8));
//        assertThat(dateTime.getDayOfMonth(), equalTo(22));
//        assertThat(dateTime.getHourOfDay(), equalTo(17));
//        assertThat(dateTime.getMinuteOfHour(), equalTo(15));
        //TODO: test
    }

    @Test
    public void testSerialize() throws Exception {
        //TODO: test
    }
}