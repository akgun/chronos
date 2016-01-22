package com.akgund.chronos;

import com.akgund.chronos.core.*;
import com.akgund.chronos.core.impl.*;
import com.akgund.chronos.service.IChronosService;
import com.akgund.chronos.service.IChronosSettingsService;
import com.akgund.chronos.service.impl.ChronosService;
import com.akgund.chronos.service.impl.FileChronosSettingsService;
import com.google.inject.AbstractModule;

public class ChronosModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IChronosSerializer.class).to(GsonChronosSerializer.class);
        bind(IChronosURI.class).to(FileChronosURI.class);
        bind(IPersistence.class).to(FilePersistence.class);
        bind(IChronosTasksDAL.class).to(FileChronosTasksDAL.class);
        bind(IChronosService.class).to(ChronosService.class);
        bind(IChronosSettingsService.class).to(FileChronosSettingsService.class);
        bind(IPathService.class).to(PathService.class);
    }
}
