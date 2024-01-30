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

@ControllerAdvice
@RequiredArgsConstructor
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(DeniedRequestException.class)
  protected ResponseEntity<Object> handle403NotFoundException(RuntimeException ex,
                                                              WebRequest request) {
    return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(),
        HttpStatus.FORBIDDEN, request);
  }

  @ExceptionHandler(NoSuchElementException.class)
  protected ResponseEntity<Object> handle404NotFoundException(RuntimeException ex,
                                                              WebRequest request) {
    return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(),
        HttpStatus.NOT_FOUND, request);
  }
}
