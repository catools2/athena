package org.catools.athena.common.controlleradvice;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
@SuppressWarnings("unused")
public final class ControllerErrorHandler extends ResponseEntityExceptionHandler {


  /**
   * Customize the handling of {@link MethodArgumentNotValidException}.
   * <p>This method delegates to {@link #handleExceptionInternal}.
   *
   * @param ex      the exception to handle
   * @param headers the headers to be written to the response
   * @param status  the selected response status
   * @param request the current request
   * @return a {@code ResponseEntity} for the response to use, possibly
   * {@code null} when the response is already committed
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    Set<FieldViolation> errors = ex.getAllErrors()
        .stream()
        .map(e -> new FieldViolation(e.getObjectName(), ((FieldError) e).getField(), StringUtils.defaultString(e.getDefaultMessage())))
        .collect(Collectors.toSet());
    return ResponseEntity.badRequest().body(errors);
  }

  private record FieldViolation(String object, String field, String violations) {
  }
}