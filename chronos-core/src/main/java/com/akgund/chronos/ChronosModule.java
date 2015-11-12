package com.akgund.chronos;

import com.akgund.chronos.core.*;
import com.akgund.chronos.core.impl.FileChronosTasksDAL;
import com.akgund.chronos.core.impl.FileChronosURI;
import com.akgund.chronos.core.impl.GsonChronosSerializer;
import com.akgund.chronos.service.impl.ChronosService;
import com.akgund.chronos.service.IChronosService;
import com.google.inject.AbstractModule;

public class ChronosModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IChronosSerializer.class).to(GsonChronosSerializer.class);
        bind(IChronosURI.class).to(FileChronosURI.class);
        bind(IChronosTasksDAL.class).to(FileChronosTasksDAL.class);
        bind(IChronosService.class).to(ChronosService.class);
    }
}
