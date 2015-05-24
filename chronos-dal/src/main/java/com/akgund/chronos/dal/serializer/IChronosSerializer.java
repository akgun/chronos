package com.akgund.chronos.dal.serializer;

import com.akgund.chronos.model.ChronosTasks;

public interface IChronosSerializer {

    String serialize(ChronosTasks chronosTasks);

    ChronosTasks deserialize(String data);
}
