package com.akgund.chronos.core;

import com.akgund.chronos.model.ChronosTasks;
import com.google.gson.Gson;

import javax.inject.Inject;

public class GsonChronosSerializer implements IChronosSerializer {
    private Gson gson = GsonProducer.newGson();

    public String serialize(ChronosTasks chronosTasks) {
        return gson.toJson(chronosTasks);
    }

    public ChronosTasks deserialize(String data) {
        return gson.fromJson(data, ChronosTasks.class);
    }
}
