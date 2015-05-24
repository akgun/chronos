package com.akgund.chronos.dal;

import com.akgund.chronos.model.ChronosTasks;
import com.google.gson.Gson;

import javax.enterprise.inject.Alternative;

@Alternative
public class MockChronosSerializer implements IChronosSerializer {

    @Override
    public String serialize(ChronosTasks chronosTasks) {
        Gson gson = new Gson();

        return gson.toJson(DummyChronosTasksFactory.createDummy());
    }

    @Override
    public ChronosTasks deserialize(String data) {
        return DummyChronosTasksFactory.createDummy();
    }
}
