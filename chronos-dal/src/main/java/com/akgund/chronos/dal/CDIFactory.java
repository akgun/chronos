package com.akgund.chronos.dal;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class CDIFactory {
    private static CDIFactory instance;
    private Weld weld;

    private CDIFactory() {
    }

    public static CDIFactory getInstance() {
        if (instance == null) {
            instance = new CDIFactory();
        }

        return instance;
    }

    public <T> T inject(Class<? extends T> type) {
        WeldContainer weldContainer = getWeld().initialize();

        return weldContainer.instance().select(type).get();
    }

    private Weld getWeld() {
        if (weld == null) {
            weld = new Weld();
        }
        return weld;
    }

    public void dispose() {
        if (weld != null) {
            weld.shutdown();
        }
    }
}
