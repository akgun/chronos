package com.akgund.chronos.dal;

import com.akgund.chronos.model.ChronosTasks;

public interface IChronosTasksDAL {

    ChronosTasks get() throws ChronosDALException;

    void save(ChronosTasks chronosTasks) throws ChronosDALException;
}
