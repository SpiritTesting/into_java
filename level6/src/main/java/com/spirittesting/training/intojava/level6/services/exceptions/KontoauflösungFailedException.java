package com.spirittesting.training.intojava.level6.services.exceptions;

public class KontoauflösungFailedException extends RuntimeException {
    public KontoauflösungFailedException() {
    }

    public KontoauflösungFailedException(String message) {
        super(message);
    }
}
