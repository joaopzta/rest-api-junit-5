package com.udemy.rest.api.services.exceptions;

public class DataIntegratyViolationException extends RuntimeException {
  public DataIntegratyViolationException(String message) {
    super(message);
  }
}
