package com.akgund.chronos.core.impl;


import com.akgund.chronos.core.IChronosSerializer;
import com.akgund.chronos.core.IChronosTasksDAL;
import com.akgund.chronos.core.IPersistence;
import com.akgund.chronos.model.ChronosTasks;
import com.google.inject.Inject;

public class FileChronosTasksDAL implements IChronosTasksDAL {
    @Inject
    private IChronosSerializer chronosSerializer;
    @Inject
    private IPersistence persistence;

    @Override
    public ChronosTasks get() throws ChronosCoreException {
        try {
            final String chronosTasksStr = persistence.read();

            if (chronosTasksStr == null || chronosTasksStr.trim().isEmpty()) {
                return new ChronosTasks();
            }

            return chronosSerializer.deserializeData(chronosTasksStr);
        } catch (ChronosCoreException e) {
            return new ChronosTasks();
        }
    }

    @Override
    public void save(ChronosTasks chronosTasks) throws ChronosCoreException {
        String data = chronosSerializer.serializeData(chronosTasks);

        persistence.write(data);
    }
}
