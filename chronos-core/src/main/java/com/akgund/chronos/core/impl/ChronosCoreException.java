package com.akgund.chronos.core.impl;

public class ChronosCoreException extends Exception {

    public ChronosCoreException() {
        super();
    }

    public ChronosCoreException(String message) {
        super(message);
    }

    public ChronosCoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChronosCoreException(Throwable cause) {
        super(cause);
    }
}
