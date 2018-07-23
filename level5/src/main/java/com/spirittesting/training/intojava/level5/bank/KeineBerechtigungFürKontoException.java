package com.spirittesting.training.intojava.level5.bank;

public class KeineBerechtigungFürKontoException extends RuntimeException {

    public KeineBerechtigungFürKontoException() {
    }

    public KeineBerechtigungFürKontoException(String message) {
        super(message);
    }

    public KeineBerechtigungFürKontoException(String message, Throwable cause) {
        super(message, cause);
    }

    public KeineBerechtigungFürKontoException(Throwable cause) {
        super(cause);
    }

    public KeineBerechtigungFürKontoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
