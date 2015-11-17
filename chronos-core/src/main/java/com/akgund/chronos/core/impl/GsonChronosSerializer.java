package com.akgund.chronos.core.impl;

import com.akgund.chronos.core.IChronosSerializer;
import com.akgund.chronos.model.ChronosTasks;
import com.akgund.chronos.model.settings.Settings;
import com.google.gson.Gson;

public class GsonChronosSerializer implements IChronosSerializer {
    private Gson gson = GsonProducer.newGson();

    public String serializeData(ChronosTasks chronosTasks) {
        return gson.toJson(chronosTasks);
    }

    public ChronosTasks deserializeData(String data) {
        return gson.fromJson(data, ChronosTasks.class);
    }

    @Override
    public String serializeSettings(Settings settings) {
        return gson.toJson(settings);
    }

    @Override
    public Settings deserializeSettings(String settings) {
        return gson.fromJson(settings, Settings.class);
    }
}
