package org.catools.athena.common.config;

import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.exception.EntityNotFoundException;
import org.catools.athena.common.exception.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Global exception handler for all controllers.
 * Converts business exceptions to appropriate HTTP status codes.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handle RecordNotFoundException - return 400 Bad Request
   * This includes: Project not found, Item not found, etc.
   */
  @ExceptionHandler(RecordNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Map<String, Object>> handleRecordNotFoundException(RecordNotFoundException ex) {
    log.warn("Record not found: {}", ex.getMessage());

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", HttpStatus.BAD_REQUEST.value());
    body.put("error", "Bad Request");
    body.put("message", ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle EntityNotFoundException - return 400 Bad Request
   * Similar to RecordNotFoundException but for entity lookups
   */
  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Map<String, Object>> handleEntityNotFoundException(EntityNotFoundException ex) {
    log.warn("Entity not found: {}", ex.getMessage());

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", HttpStatus.BAD_REQUEST.value());
    body.put("error", "Bad Request");
    body.put("message", ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle IllegalArgumentException - return 400 Bad Request
   * For validation errors and invalid input
   */
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
    log.warn("Invalid argument: {}", ex.getMessage());

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", HttpStatus.BAD_REQUEST.value());
    body.put("error", "Bad Request");
    body.put("message", ex.getMessage() != null ? ex.getMessage() : "Invalid argument provided");

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle MethodArgumentNotValidException - return 400 Bad Request
   * For @Valid and @Validated bean validation failures
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<List<Map<String, Object>>> handleValidationException(MethodArgumentNotValidException ex) {
    log.warn("Validation failed: {}", ex.getMessage());

    List<Map<String, Object>> errors = new ArrayList<>();

    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      Map<String, Object> errorDetail = new LinkedHashMap<>();
      errorDetail.put("object", error.getObjectName());
      errorDetail.put("field", error.getField());
      errorDetail.put("rejectedValue", error.getRejectedValue());
      errorDetail.put("violations", error.getDefaultMessage());
      errors.add(errorDetail);
    }

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle all other exceptions - return 500 Internal Server Error
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
    log.error("Unexpected error occurred", ex);

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    body.put("error", "Internal Server Error");
    body.put("message", "An unexpected error occurred. Please contact support.");

    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

