package com.akgund.chronos.dal;

import com.akgund.chronos.model.ChronosTasks;

import javax.enterprise.inject.Alternative;

@Alternative
public class MockChronosTasksDAL implements IChronosTasksDAL {

    @Override
    public ChronosTasks get() throws ChronosDALException {
        return DummyChronosTasksFactory.createDummy();
    }

    @Override
    public void save(ChronosTasks chronosTasks) throws ChronosDALException {
    }
}
