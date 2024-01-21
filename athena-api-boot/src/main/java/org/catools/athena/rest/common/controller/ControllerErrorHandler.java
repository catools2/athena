package org.catools.athena.rest.common.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.defaultString;

/**
 * A list of 'quality center' related resources.
 */
@ControllerAdvice
@SuppressWarnings("unused")
public final class ControllerErrorHandler {

    @ExceptionHandler(TransactionSystemException.class)
    ResponseEntity<?> handleTransactionSystemException(TransactionSystemException ex) {

        if (ex.getCause().getCause() instanceof ConstraintViolationException cx) {
            Set<Map<String, String>> errors = cx.getConstraintViolations()
                    .stream()
                    .map(v -> Map.of(v.getPropertyPath().toString(), v.getMessage()))
                    .collect(Collectors.toSet());
            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Set<Map<String, String>> errors = ex.getFieldErrors()
                .stream()
                .map(e -> Map.of(e.getField(), defaultString(e.getDefaultMessage())))
                .collect(Collectors.toSet());
        return ResponseEntity.badRequest().body(errors);
    }
}