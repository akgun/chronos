package com.akgund.chronos.core;

import com.akgund.chronos.core.impl.ChronosCoreException;

public interface IPersistence {

    String read() throws ChronosCoreException;

    void write(String data) throws ChronosCoreException;
}
