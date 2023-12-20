package org.catools.athena.rest.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.testcontainers.shaded.org.apache.commons.lang3.StringUtils.defaultString;

/**
 * A list of 'quality center' related resources.
 */
@ControllerAdvice
@SuppressWarnings("unused")
public final class AthenaControllerErrorHandler {

  @ExceptionHandler(TransactionSystemException.class)
  ResponseEntity<?> handleJpaTransactionViolation(TransactionSystemException ex) {

    if (ex.getCause().getCause() instanceof ConstraintViolationException cx) {
      List<Map<String, String>> errors = cx.getConstraintViolations().stream().map(v -> Map.of(v.getPropertyPath().toString(), v.getMessage())).collect(Collectors.toList());
      return ResponseEntity.badRequest().body(errors);
    }

    return ResponseEntity.badRequest().build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<?> handleMethodArgumentValidation(MethodArgumentNotValidException ex) {
    List<Map<String, String>> errors = ex.getFieldErrors().stream().map(e -> Map.of(e.getField(), defaultString(e.getDefaultMessage()))).collect(Collectors.toList());
    return ResponseEntity.badRequest().body(errors);
  }
}