package com.akgund.chronos.service;

public class ChronosServiceException extends Exception {

    public ChronosServiceException() {
        super();
    }

    public ChronosServiceException(String message) {
        super(message);
    }

    public ChronosServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChronosServiceException(Throwable cause) {
        super(cause);
    }

    protected ChronosServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
