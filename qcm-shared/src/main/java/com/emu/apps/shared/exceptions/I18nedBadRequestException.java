package com.emu.apps.shared.exceptions;

public class I18nedBadRequestException extends I18nedRunTimeMessageException {

    public I18nedBadRequestException(String message) {
        super(null, message);
    }

    public I18nedBadRequestException(String message, String uuid) {
        super(message, uuid);
    }

}
