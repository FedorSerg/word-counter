package com.example.generativeai.exception;

import java.io.Serial;

public class DeniedRequestException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 7500013200692903763L;

  public DeniedRequestException(String message) {
    super(message);
  }

}
