package com.emu.apps.qcm.spi.webmvc.config;

import com.emu.apps.qcm.spi.persistence.exceptions.EntityNotFoundException;
import com.emu.apps.qcm.spi.webmvc.exceptions.ExceptionMessage;
import com.emu.apps.qcm.spi.webmvc.exceptions.FieldErrorMessage;
import com.emu.apps.qcm.spi.webmvc.exceptions.UserAuthenticationException;
import com.emu.apps.qcm.spi.webmvc.exceptions.builders.ExceptionMessageBuilder;
import org.jetbrains.annotations.NotNull;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.lang.reflect.InvocationTargetException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Profile("webmvc")
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseBody
    public ResponseEntity <Object> handleEntityNotFoundException(Exception e) {

        LOG.error("EntityNotFoundException caught: {}", e.getMessage());

        ExceptionMessage exceptionMessage = new ExceptionMessageBuilder()
                .setStatus(NOT_FOUND.value())
                .setException(NOT_FOUND.getReasonPhrase())
                .setError(e.getClass().getName())
                .setTimestamp(ZonedDateTime.now())
                .setMessage(e.getMessage()).createExceptionMessage();

        return response(exceptionMessage, NOT_FOUND);
    }


    /**
     * Catch all for any other exceptions...
     * @param e Exception.class
     * @return Send a 500 Internal Server Error
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity <Object> handleAnyException(Exception e) {

        LOG.error("AnyException caught: ", e);

        ExceptionMessage exceptionMessage = new ExceptionMessageBuilder()
                .setStatus(INTERNAL_SERVER_ERROR.value())
                .setException(INTERNAL_SERVER_ERROR.getReasonPhrase())
                .setError(e.getClass().getName())
                .setTimestamp(ZonedDateTime.now())
                .setMessage(e.getMessage()).createExceptionMessage();

        return response(exceptionMessage, INTERNAL_SERVER_ERROR);
    }


    /**
     * Handle failures commonly thrown from code
     *
     * @param t InvocationTargetException.class, IllegalArgumentException.class, ClassCastException.class, ConversionFailedException.class
     * @return Send a 400 Bad Request
     */
    @ExceptionHandler({InvocationTargetException.class, IllegalArgumentException.class,
            ClassCastException.class,
            ConversionFailedException.class})
    @ResponseBody
    public ResponseEntity<ExceptionMessage> handleMiscFailures(Throwable t) {

        LOG.error("Misc Exception caught: ", t);

        ExceptionMessage exceptionMessage = new ExceptionMessageBuilder()
                .setStatus(BAD_REQUEST.value())
                .setError(BAD_REQUEST.getReasonPhrase())
                .setException(t.getClass().getName())
                .setTimestamp(ZonedDateTime.now())
                .setMessage(t.getMessage()).createExceptionMessage();

        return response(exceptionMessage, BAD_REQUEST);
    }


    /**
     * Catch conflict Exception
     *
     * @param ex ObjectOptimisticLockingFailureException.class,
     *             OptimisticLockingFailureException.class,
     *             DataIntegrityViolationException.class
     * @return Send a 409 Conflict in case of concurrent modification
     */
    @ExceptionHandler({ObjectOptimisticLockingFailureException.class,
            OptimisticLockingFailureException.class,
            DataIntegrityViolationException.class})
    @ResponseBody
    public ResponseEntity<ExceptionMessage> handleConflict(Exception ex) {

        LOG.error("Conflict Exception caught: ", ex);

        ExceptionMessage response = new ExceptionMessageBuilder()
                .setStatus(CONFLICT.value())
                .setError(CONFLICT.getReasonPhrase())
                .setException(ex.getClass().getName())
                .setMessage(ex.getMessage())
                .setTimestamp(ZonedDateTime.now()).createExceptionMessage();

        return response(response, CONFLICT);
    }

    protected <T> ResponseEntity <T> response(T body, HttpStatus status) {
        LOG.debug("Responding with a status of {}", status);
        return new ResponseEntity <>(body, new HttpHeaders(), status);
    }

    /**
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return Send a 400 Bad Request
     */
    @Override
    protected @NotNull ResponseEntity <Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                            @NotNull HttpHeaders headers, @NotNull HttpStatus status,
                                                                            @NotNull WebRequest request) {

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
                .setTimestamp(ZonedDateTime.now())
                .setMessage(ex.getMessage())
                .setErrors(fieldErrors).createExceptionMessage();

        return response(response, BAD_REQUEST);

    }


    @ExceptionHandler({UserAuthenticationException.class})
    @ResponseBody
    public ResponseEntity <Object> handleAuthenticationException(Exception e) {

        LOG.error("AnyException caught: ", e);

        ExceptionMessage exceptionMessage = new ExceptionMessageBuilder()
                .setStatus(FORBIDDEN.value())
                .setException(FORBIDDEN.getReasonPhrase())
                .setError(e.getClass().getName())
                .setTimestamp(ZonedDateTime.now())
                .setMessage(e.getMessage()).createExceptionMessage();

        return response(exceptionMessage, FORBIDDEN);
    }

    /**
     * ref
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {

        LOG.error("ConstraintViolationException caught: ", e);

        ExceptionMessage exceptionMessage = new ExceptionMessageBuilder()
                .setStatus(BAD_REQUEST.value())
                .setException(BAD_REQUEST.getReasonPhrase())
                .setError(e.getClass().getName())
                .setTimestamp(ZonedDateTime.now())
                .setMessage(e.getMessage()).createExceptionMessage();

        return response(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

}
