package com.akgund.chronos.core.impl;

import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DateTimeJsonConverterTest {
    private DateTimeJsonConverter dateTimeJsonConverter;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        dateTimeJsonConverter = new DateTimeJsonConverter();
    }

    @Test
    public void whenNullThenDeserializeThrowsException() throws Exception {
        exception.expect(Exception.class);

        dateTimeJsonConverter.deserialize(null);
    }

    @Test
    public void whenStrThenSerializeReturnsObject() throws Exception {
        final JsonObject jsonObject = dateTimeJsonConverter.serialize("naber");

        assertThat(jsonObject.get(DateTimeJsonConverter.DATE_TIME_KEY).getAsString(), equalTo("naber"));
    }
}