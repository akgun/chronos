package com.akgund.chronos.core.impl;

import com.akgund.chronos.core.IChronosSerializer;
import com.akgund.chronos.model.ChronosTasks;
import com.google.gson.Gson;

public class GsonChronosSerializer implements IChronosSerializer {
    private Gson gson = GsonProducer.newGson();

    public String serialize(ChronosTasks chronosTasks) {
        return gson.toJson(chronosTasks);
    }

    public ChronosTasks deserialize(String data) {
        return gson.fromJson(data, ChronosTasks.class);
    }
}
