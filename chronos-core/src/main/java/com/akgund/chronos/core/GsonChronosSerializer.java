package com.akgund.chronos.core;

import com.akgund.chronos.model.ChronosTasks;
import com.google.gson.Gson;

public class GsonChronosSerializer implements IChronosSerializer {

    public String serialize(ChronosTasks chronosTasks) {
        Gson gson = new Gson();

        return gson.toJson(chronosTasks);
    }

    public ChronosTasks deserialize(String data) {
        Gson gson = new Gson();

        return gson.fromJson(data, ChronosTasks.class);
    }
}
