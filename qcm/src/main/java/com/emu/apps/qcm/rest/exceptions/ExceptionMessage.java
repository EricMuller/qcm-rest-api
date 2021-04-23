package com.emu.apps.qcm.rest.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.ZonedDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
@Getter
public final class ExceptionMessage {

    @JsonProperty("status")
    private final int status;

    @JsonProperty("error")
    private final String error;

    @JsonProperty("exception")
    private final String exception;

    @JsonProperty("message")
    private final String message;

    @JsonProperty("errors")
    private final List <FieldErrorMessage> errors;

    @JsonProperty("timestamp")
    @JsonFormat(shape = STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final ZonedDateTime timestamp;

    public ExceptionMessage(int status, String error, String message, String exception,
                            List <FieldErrorMessage> fieldErrors, ZonedDateTime timestamp) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.exception = exception;
        this.errors = fieldErrors;
        this.timestamp = timestamp;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("status", status)
                .append("error", error)
                .append("exception", exception)
                .append("message", message)
                .append("errors", errors)
                .append("timestamp", timestamp)
                .toString();
    }
}
