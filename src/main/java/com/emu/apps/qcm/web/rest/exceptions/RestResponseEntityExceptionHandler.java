package com.emu.apps.qcm.web.rest.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    /**
     * Catch all for any other exceptions...
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity<?> handleAnyException(Exception e) {
        ExceptionMessage exceptionMessage = new ExceptionMessageBuilder()
        .setStatus(INTERNAL_SERVER_ERROR.value())
        .setException(INTERNAL_SERVER_ERROR.getReasonPhrase())
        .setError(e.getClass().getName())
        .setTimestamp(LocalDate.now())
        .setMessage(e.getMessage()).createExceptionMessage();

        return errorResponse(exceptionMessage, INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle failures commonly thrown from code
     */
    @ExceptionHandler({InvocationTargetException.class, IllegalArgumentException.class, ClassCastException.class,
            ConversionFailedException.class})
    @ResponseBody
    public ResponseEntity handleMiscFailures(Throwable t) {

        ExceptionMessage exceptionMessage = new ExceptionMessageBuilder()
        .setStatus(BAD_REQUEST.value())
        .setError(BAD_REQUEST.getReasonPhrase())
        .setException(t.getClass().getName())
        .setTimestamp(LocalDate.now())
        .setMessage(t.getMessage()).createExceptionMessage();

        return errorResponse(exceptionMessage, BAD_REQUEST);
    }

    /**
     * Send a 409 Conflict in case of concurrent modification
     */
    @ExceptionHandler({ObjectOptimisticLockingFailureException.class, OptimisticLockingFailureException.class,
            DataIntegrityViolationException.class})
    @ResponseBody
    public ResponseEntity handleConflict(Exception ex) {

        ExceptionMessage response = new ExceptionMessageBuilder()
        .setStatus(CONFLICT.value())
        .setError(CONFLICT.getReasonPhrase())
        .setException(ex.getClass().getName())
        .setMessage(ex.getMessage())
        .setTimestamp(LocalDate.now()).createExceptionMessage();

        return errorResponse(response, CONFLICT);
    }

    protected ResponseEntity<Object> errorResponse(ExceptionMessage response,
                                                             HttpStatus status) {
        if (null != response) {
            log.error("error caught: " + response.toString());
            return response(response, status);
        } else {
            log.error("unknown error caught in RESTController, {}", status);
            return response(null, status);
        }
    }

    protected <T> ResponseEntity<T> response(T body, HttpStatus status) {
        log.debug("Responding with a status of {}", status);
        return new ResponseEntity<>(body, new HttpHeaders(), status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors().stream().map(fieldError ->
                new FieldError(
                        fieldError.getObjectName(),
                        fieldError.getField(),
                        fieldError.getCode(),
                        fieldError.getDefaultMessage())).collect(Collectors.toList());

        ExceptionMessage response = new ExceptionMessageBuilder()
        .setStatus(BAD_REQUEST.value())
        .setError(BAD_REQUEST.getReasonPhrase())
        .setException(ex.getClass().getName())
        .setTimestamp(LocalDate.now())
        .setMessage(ex.getMessage())
        .setErrors(fieldErrors).createExceptionMessage();
        return errorResponse(response, BAD_REQUEST);

    }

}