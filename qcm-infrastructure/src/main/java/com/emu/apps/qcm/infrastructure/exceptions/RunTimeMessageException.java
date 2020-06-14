package com.emu.apps.qcm.infrastructure.exceptions;

/**
 * internationalisation
 */
public class RunTimeMessageException extends RuntimeException {

    private String codeMessage; // code message

    private String uuid; // code message

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
