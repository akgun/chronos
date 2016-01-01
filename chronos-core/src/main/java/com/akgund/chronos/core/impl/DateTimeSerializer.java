package com.akgund.chronos.core.impl;

import com.google.gson.*;
import org.joda.time.DateTime;

import java.lang.reflect.Type;

public class DateTimeSerializer implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {
    private DateTimeJsonConverter dateTimeJsonConverter = new DateTimeJsonConverter();
    private DateTimeParser dateTimeParser = new DateTimeParser();

    @Override
    public DateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final String dateTimeStr = dateTimeJsonConverter.deserialize(jsonElement);

        return dateTimeParser.parse(dateTimeStr);
    }

    @Override
    public JsonElement serialize(DateTime dateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        final String dateTimeStr = dateTimeParser.toString(dateTime);

        return dateTimeJsonConverter.serialize(dateTimeStr);
    }
}
