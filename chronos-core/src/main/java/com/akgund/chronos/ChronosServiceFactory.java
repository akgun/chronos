package com.akgund.chronos;

import com.akgund.chronos.service.IChronosService;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ChronosServiceFactory {

    public static IChronosService create() {
        Injector injector = Guice.createInjector(new ChronosModule());

        return injector.getInstance(IChronosService.class);
    }
}
