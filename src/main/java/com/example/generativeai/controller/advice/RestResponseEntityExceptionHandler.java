package com.example.generativeai.controller.advice;

import com.example.generativeai.exception.DeniedRequestException;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler for the REST controllers.
 */
@ControllerAdvice
@RequiredArgsConstructor
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handles DeniedRequestException and returns a FORBIDDEN response.
   */
  @ExceptionHandler(DeniedRequestException.class)
  protected ResponseEntity<Object> handle403Forbidden(RuntimeException ex,
                                                      WebRequest request) {
    return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(),
        HttpStatus.FORBIDDEN, request);
  }

  /**
   * Handles NoSuchElementException and returns a NOT_FOUND response.
   */
  @ExceptionHandler(NoSuchElementException.class)
  protected ResponseEntity<Object> handle404NotFoundException(RuntimeException ex,
                                                              WebRequest request) {
    return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(),
        HttpStatus.NOT_FOUND, request);
  }

  /**
   * Handles generic exceptions and returns an INTERNAL_SERVER_ERROR response.
   */
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Object> handle500InternalServerError(Exception ex,
                                                                WebRequest request) {
    return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(),
        HttpStatus.INTERNAL_SERVER_ERROR, request);
  }
}
