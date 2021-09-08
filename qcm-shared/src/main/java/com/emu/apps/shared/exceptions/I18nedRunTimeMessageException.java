package com.emu.apps.shared.exceptions;

import lombok.Getter;

/**
 * internationalisation
 */
@Getter
public class I18nedRunTimeMessageException extends RuntimeException {

    private final String codeMessage; // code message

    private final Object[] args;

    public I18nedRunTimeMessageException(String message, String... args) {
        super(message);
        this.codeMessage = message;
        this.args = args;
    }


}
