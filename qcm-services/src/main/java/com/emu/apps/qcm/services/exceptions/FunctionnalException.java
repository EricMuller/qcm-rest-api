package com.emu.apps.qcm.services.exceptions;

public class FunctionnalException extends Exception {
    public FunctionnalException(String message) {
        super(message);
    }

    public FunctionnalException(String message, Throwable cause) {
        super(message, cause);
    }
}
