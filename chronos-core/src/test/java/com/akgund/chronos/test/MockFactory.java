package com.akgund.chronos.test;

import com.akgund.chronos.core.IChronosURI;
import com.akgund.chronos.service.IChronosService;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class MockFactory {

    public static IChronosService createChronosService() {
        return getInjector().getInstance(IChronosService.class);
    }

    private static Injector getInjector() {
        return Guice.createInjector(new MockChronosModule());
    }

    public static IChronosURI createChoronosURI() {
        return getInjector().getInstance(IChronosURI.class);
    }
}
