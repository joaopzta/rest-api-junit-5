package com.udemy.rest.api.controllers.exceptions;

import com.udemy.rest.api.services.exceptions.DataIntegrityViolationException;
import com.udemy.rest.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
@DisplayName("Garante o funcionamento da classe ControllerExceptionHandler")
class ControllerExceptionHandlerTest {

  private static final String OBJECT_NOT_FOUND     = "Objeto não encontrado!";
  private static final String EMAIL_ALREADY_EXISTS = "O email informado já existe no sistema";

  @InjectMocks
  private ControllerExceptionHandler exceptionHandler;

  @BeforeEach
  void setUp() {
  }

  @Test
  @DisplayName("Deve lançar exceção ObjectNotFoundException e retornar NOT_FOUND")
  void must_throw_ObjectNotFoundException_and_return_ResponseEntity() {
    var response = exceptionHandler.objectNotFound(
            new ObjectNotFoundException(OBJECT_NOT_FOUND),
            new MockHttpServletRequest()
    );

    assertNotNull(response);
    assertNotNull(response.getBody());
    assertEquals(NOT_FOUND, response.getStatusCode());
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(StandartError.class, response.getBody().getClass());
    assertEquals(OBJECT_NOT_FOUND, response.getBody().getError());
  }

  @Test
  @DisplayName("Deve lançar exceção DataIntegrityViolationException e retornar BAD_REQUEST")
  void must_throw_DataIntegrityViolationException_and_return_ResponseEntity() {
    var response = exceptionHandler.dataIntegrityViolation(
            new DataIntegrityViolationException(EMAIL_ALREADY_EXISTS),
            new MockHttpServletRequest()
    );

    assertNotNull(response);
    assertNotNull(response.getBody());
    assertEquals(BAD_REQUEST, response.getStatusCode());
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(StandartError.class, response.getBody().getClass());
    assertEquals(EMAIL_ALREADY_EXISTS, response.getBody().getError());
  }

}