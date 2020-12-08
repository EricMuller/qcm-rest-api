package com.emu.apps.qcm.infra.persistence.exceptions;

/**
 * internationalisation
 */
public class RunTimeMessageException extends RuntimeException {

    private final String codeMessage; // code message

    private final String uuid; // code message

    public RunTimeMessageException(String uuid, String message) {
        super(message);
        this.codeMessage = message;
        this.uuid = uuid;
    }

    public String getCodeMessage() {
        return codeMessage;
    }

    public String getUuid() {
        return uuid;
    }
}
