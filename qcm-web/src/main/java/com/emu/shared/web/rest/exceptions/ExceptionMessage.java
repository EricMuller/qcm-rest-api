package com.emu.shared.web.rest.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionMessage {

    @JsonProperty("status")
    private int status;
    @JsonProperty("error")
    private String error;
    @JsonProperty("exception")
    private String exception;
    @JsonProperty("message")
    private String message;
    @JsonProperty("errors")
    private List<FieldErrorMessage> errors;
    @JsonProperty("timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDate timestamp;

    public ExceptionMessage() {
    }


    public ExceptionMessage(int status, String error, String message, String exception,
                            List<FieldErrorMessage> fieldErrors, LocalDate timestamp) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.exception = exception;
        this.errors = fieldErrors;
        this.timestamp = timestamp;
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public List<FieldErrorMessage> getErrors() {
        return errors;
    }

    public void setErrors(List<FieldErrorMessage> errors) {
        this.errors = errors;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
