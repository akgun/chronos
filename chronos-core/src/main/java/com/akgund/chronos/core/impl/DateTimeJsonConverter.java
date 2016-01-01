package com.akgund.chronos.core.impl;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class DateTimeJsonConverter {
    public static final String DATE_TIME_KEY = "dateTime";

    public String deserialize(JsonElement jsonElement) {
        return jsonElement.getAsJsonObject().get(DATE_TIME_KEY).getAsString();
    }

    public JsonObject serialize(String dateTime) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(DATE_TIME_KEY, dateTime);

        return jsonObject;
    }
}
