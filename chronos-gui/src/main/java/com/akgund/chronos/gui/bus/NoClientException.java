package com.akgund.chronos.gui.bus;

public class NoClientException extends RuntimeException {

    public NoClientException() {
        super();
    }

    public NoClientException(String message) {
        super(message);
    }

    public NoClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoClientException(Throwable cause) {
        super(cause);
    }

    protected NoClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
