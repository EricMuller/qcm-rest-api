package com.emu.apps.qcm.webmvc.exceptions.builders;

import com.emu.apps.qcm.webmvc.exceptions.ExceptionMessage;
import com.emu.apps.qcm.webmvc.exceptions.FieldErrorMessage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ExceptionMessageBuilder {
    private int status;

    private String error;

    private String message;

    private String exception;

    private List<FieldErrorMessage> errors;

    private LocalDateTime timestamp;

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

    public ExceptionMessageBuilder setErrors(List<FieldErrorMessage> errors) {
        this.errors = errors;
        return this;
    }

    public ExceptionMessageBuilder setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public ExceptionMessage createExceptionMessage() {
        return new ExceptionMessage(status, error, message, exception, errors, timestamp);
    }
}
