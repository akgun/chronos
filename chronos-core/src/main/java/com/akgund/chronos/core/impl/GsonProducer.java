package com.akgund.chronos.core.impl;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.joda.time.DateTime;

public class GsonProducer {

    public static Gson newGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeSerializer());
        gsonBuilder.setPrettyPrinting();

        return gsonBuilder.create();
    }
}
