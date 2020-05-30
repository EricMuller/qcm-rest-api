package com.emu.apps.qcm.infrastructure.exceptions;

public class FunctionnalException extends Exception {
    public FunctionnalException(String message) {
        super(message);
    }

    public FunctionnalException(String message, Throwable cause) {
        super(message, cause);
    }
}
