package com.emu.apps.shared.web.rest.exceptions;

// @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Resource")
public class ResourceNotFoundException extends   RuntimeException{

    public ResourceNotFoundException() {
        super();
    }
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
