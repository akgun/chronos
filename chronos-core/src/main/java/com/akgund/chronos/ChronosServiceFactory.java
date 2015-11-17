package com.akgund.chronos;

import com.akgund.chronos.service.IChronosService;
import com.akgund.chronos.service.IChronosSettingsService;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ChronosServiceFactory {

    public static IChronosService create() {
        return getInjector().getInstance(IChronosService.class);
    }

    public static IChronosSettingsService createSettings() {
        return getInjector().getInstance(IChronosSettingsService.class);
    }

    private static Injector getInjector() {
        return Guice.createInjector(new ChronosModule());
    }
}
