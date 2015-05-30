package com.akgund.chronos.core;

import com.google.gson.*;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.lang.reflect.Type;

public class DateTimeSerializer implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {

    @Override
    public DateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String string = jsonElement.getAsJsonObject().get("dateTime").getAsString();
        return ISODateTimeFormat.dateTime().parseDateTime(string);
    }

    @Override
    public JsonElement serialize(DateTime dateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("dateTime", ISODateTimeFormat.dateTime().print(dateTime));

        return jsonObject;
    }
}
