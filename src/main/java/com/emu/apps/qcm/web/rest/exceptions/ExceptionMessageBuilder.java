package com.emu.apps.qcm.web.rest.exceptions;

import java.time.LocalDate;
import java.util.List;

public class ExceptionMessageBuilder {
    private int status;
    private String error;
    private String message;
    private String exception;
    private List<FieldError> errors;
    private LocalDate timestamp;

    public ExceptionMessageBuilder setStatus(int status) {
        this.status = status;
        return this;
    }

    public ExceptionMessageBuilder setError(String error) {
        this.error = error;
        return this;
    }

    public ExceptionMessageBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public ExceptionMessageBuilder setException(String exception) {
        this.exception = exception;
        return this;
    }

    public ExceptionMessageBuilder setErrors(List<FieldError> errors) {
        this.errors = errors;
        return this;
    }

    public ExceptionMessageBuilder setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public ExceptionMessage createExceptionMessage() {
        return new ExceptionMessage(status, error, message, exception, errors, timestamp);
    }
}