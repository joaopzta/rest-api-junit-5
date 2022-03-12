package com.udemy.rest.api.controllers.exceptions;

import com.udemy.rest.api.services.exceptions.DataIntegratyViolationException;
import com.udemy.rest.api.services.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(ObjectNotFoundException.class)
  public ResponseEntity<StandartError> objectNotFound(ObjectNotFoundException ex, HttpServletRequest request) {
    var error = buildStandartError(ex.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(DataIntegratyViolationException.class)
  public ResponseEntity<StandartError> objectNotFound(DataIntegratyViolationException ex, HttpServletRequest request) {
    var error = buildStandartError(ex.getMessage(), request.getRequestURI(), HttpStatus.BAD_REQUEST);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  private StandartError buildStandartError(String message, String path, HttpStatus status) {
    return StandartError.builder()
            .timestamp(LocalDateTime.now())
            .error(message)
            .statusCode(status.value())
            .path(path)
            .build();
  }

}
