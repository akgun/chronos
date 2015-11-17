package com.akgund.chronos.core;

import com.akgund.chronos.model.ChronosTasks;
import com.akgund.chronos.model.settings.Settings;

public interface IChronosSerializer {

    String serializeData(ChronosTasks chronosTasks);

    ChronosTasks deserializeData(String data);

    String serializeSettings(Settings settings);

    Settings deserializeSettings(String settings);
}
