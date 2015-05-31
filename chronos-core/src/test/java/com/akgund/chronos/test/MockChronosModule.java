package com.akgund.chronos.test;

import com.akgund.chronos.core.*;
import com.akgund.chronos.service.ChronosService;
import com.akgund.chronos.service.IChronosService;
import com.google.inject.AbstractModule;

public class MockChronosModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IChronosURI.class).to(MockChronosURI.class);
        bind(IChronosSerializer.class).to(GsonChronosSerializer.class);
        bind(IChronosTasksDAL.class).to(FileChronosTasksDAL.class);
        bind(IChronosService.class).to(ChronosService.class);
    }
}
