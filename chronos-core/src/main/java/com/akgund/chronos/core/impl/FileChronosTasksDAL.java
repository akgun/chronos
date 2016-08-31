package com.akgund.chronos.core.impl;


import com.akgund.chronos.core.IChronosSerializer;
import com.akgund.chronos.core.IChronosTasksDAL;
import com.akgund.chronos.core.IPersistence;
import com.akgund.chronos.model.ChronosTasks;
import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileChronosTasksDAL implements IChronosTasksDAL {
    private static final Logger logger = LogManager.getLogger(FileChronosTasksDAL.class);

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
            logger.debug("No tasks. Creating.");
            return new ChronosTasks();
        }
    }

    @Override
    public void save(ChronosTasks chronosTasks) throws ChronosCoreException {
        String data = chronosSerializer.serializeData(chronosTasks);

        persistence.write(data);
    }
}
