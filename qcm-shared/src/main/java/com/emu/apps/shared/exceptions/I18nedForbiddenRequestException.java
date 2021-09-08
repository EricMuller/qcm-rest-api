package com.emu.apps.shared.exceptions;

public class I18nedForbiddenRequestException extends I18nedRunTimeMessageException {

    public I18nedForbiddenRequestException(String message) {
        super(null, message);
    }

    public I18nedForbiddenRequestException(String message, String uuid) {
        super(message, uuid);
    }

}
