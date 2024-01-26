package org.catools.athena.rest.common.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
@SuppressWarnings("unused")
public final class ControllerErrorHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({TransactionSystemException.class})
  public ResponseEntity<Object> handleConstraintViolation(TransactionSystemException ex, WebRequest request) {
    if (ex.getCause().getCause() instanceof ConstraintViolationException cx) {
      Set<Map<String, String>> errors = cx.getConstraintViolations()
          .stream()
          .map(v -> Map.of(v.getPropertyPath().toString(), v.getMessage()))
          .collect(Collectors.toSet());
      return ResponseEntity.badRequest().body(errors);
    }

    return ResponseEntity.badRequest().build();
  }

}