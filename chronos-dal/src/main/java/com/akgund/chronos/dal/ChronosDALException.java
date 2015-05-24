package com.akgund.chronos.dal;

public class ChronosDALException extends Exception {

    public ChronosDALException() {
        super();
    }

    public ChronosDALException(String message) {
        super(message);
    }

    public ChronosDALException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChronosDALException(Throwable cause) {
        super(cause);
    }
}
