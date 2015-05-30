package com.akgund.chronos.core;

import com.akgund.chronos.model.ChronosTasks;

public interface IChronosTasksDAL {

    ChronosTasks get() throws ChronosCoreException;

    void save(ChronosTasks chronosTasks) throws ChronosCoreException;
}
