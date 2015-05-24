package com.akgund.chronos.dal;

import com.akgund.chronos.model.ChronosTasks;

public interface IChronosTasksDAL {
    ChronosTasks get();

    void save(ChronosTasks chronosTasks);
}
