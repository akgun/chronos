package com.akgund.chronos.core;

import com.akgund.chronos.model.ChronosTasks;

public interface IChronosSerializer {

    String serialize(ChronosTasks chronosTasks);

    ChronosTasks deserialize(String data);
}
