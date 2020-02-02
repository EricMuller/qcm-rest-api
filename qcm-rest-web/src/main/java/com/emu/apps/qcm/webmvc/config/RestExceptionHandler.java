package com.emu.apps.qcm.webmvc.config;

import com.emu.apps.qcm.webmvc.exceptions.ExceptionMessage;
import com.emu.apps.qcm.webmvc.exceptions.FieldErrorMessage;
import com.emu.apps.qcm.webmvc.exceptions.builders.ExceptionMessageBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
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
@Profile("webmvc")
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    /**
     * Catch all for any other exceptions...
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity <Object> handleAnyException(Exception e) {

        LOGGER.error("Exception caught: {}", e);

        ExceptionMessage exceptionMessage = new ExceptionMessageBuilder()
                .setStatus(INTERNAL_SERVER_ERROR.value())
                .setException(INTERNAL_SERVER_ERROR.getReasonPhrase())
                .setError(e.getClass().getName())
                .setTimestamp(LocalDate.now())
                .setMessage(e.getMessage()).createExceptionMessage();

        return response(exceptionMessage, INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle failures commonly thrown from code
     */
    @ExceptionHandler({InvocationTargetException.class, IllegalArgumentException.class,
            ClassCastException.class,
            ConversionFailedException.class})
    @ResponseBody
    public ResponseEntity handleMiscFailures(Throwable t) {

        LOGGER.error("Misc Exception caught: {}", t);

        ExceptionMessage exceptionMessage = new ExceptionMessageBuilder()
                .setStatus(BAD_REQUEST.value())
                .setError(BAD_REQUEST.getReasonPhrase())
                .setException(t.getClass().getName())
                .setTimestamp(LocalDate.now())
                .setMessage(t.getMessage()).createExceptionMessage();

        return response(exceptionMessage, BAD_REQUEST);
    }

    /**
     * Send a 409 Conflict in case of concurrent modification
     */
    @ExceptionHandler({ObjectOptimisticLockingFailureException.class,
            OptimisticLockingFailureException.class,
            DataIntegrityViolationException.class})
    @ResponseBody
    public ResponseEntity handleConflict(Exception ex) {

        LOGGER.error("Conflict Exception caught: {}", ex);

        ExceptionMessage response = new ExceptionMessageBuilder()
                .setStatus(CONFLICT.value())
                .setError(CONFLICT.getReasonPhrase())
                .setException(ex.getClass().getName())
                .setMessage(ex.getMessage())
                .setTimestamp(LocalDate.now()).createExceptionMessage();

        return response(response, CONFLICT);
    }

    protected <T> ResponseEntity <T> response(T body, HttpStatus status) {
        LOGGER.debug("Responding with a status of {}", status);
        return new ResponseEntity <>(body, new HttpHeaders(), status);
    }

    @Override
    protected ResponseEntity <Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                   HttpHeaders headers, HttpStatus status,
                                                                   WebRequest request) {

        List <FieldErrorMessage> fieldErrors = ex.getBindingResult().getFieldErrors().stream().map(fieldError ->
                new FieldErrorMessage(
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

        return response(response, BAD_REQUEST);

    }

}
