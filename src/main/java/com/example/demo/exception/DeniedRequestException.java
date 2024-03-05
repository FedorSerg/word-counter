package com.example.demo.exception;

import java.io.Serial;

/**
 * Exception thrown when a request is denied.
 * This exception is typically used to indicate that a certain operation or request
 * is not allowed or denied due to specific conditions (usually authorization).
 */
public class DeniedRequestException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 7500013200692903763L;

  public DeniedRequestException(String message) {
    super(message);
  }

}
