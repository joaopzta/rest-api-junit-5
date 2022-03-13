package com.udemy.rest.api.controllers;

import com.udemy.rest.api.model.User;
import com.udemy.rest.api.model.dto.UserDTO;
import com.udemy.rest.api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
@DisplayName("Garante o funcionamento da classe UserController")
class UserControllerTest {

  private static final Integer ID      = 1;
  private static final String NAME     = "Joao";
  private static final String EMAIL    = "joao@gmail.com";
  private static final String PASSWORD = "1234";
  private static final String USER_URI = "http://localhost:8080/user/1";

  private UserController controller;

  @Mock
  private UserServiceImpl service;

  @Mock
  private ModelMapper mapper;

  private User user;
  private UserDTO userDTO;

  @BeforeEach
  void setUp() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    controller = new UserController(service, mapper);
    setupUsers();
  }

  @Test
  @DisplayName("Deve retornar um usuário pelo ID com status OK")
  void must_find_user_by_id_with_status_OK() {
    given(service.findById(anyInt())).willReturn(user);
    given(mapper.map(any(), any())).willReturn(userDTO);

    var response = controller.findById(ID);

    assertNotNull(response);
    assertNotNull(response.getBody());
    assertEquals(OK, response.getStatusCode());
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(UserDTO.class, response.getBody().getClass());
    assertFields(response.getBody());
  }

  @Test
  @DisplayName("Deve retornar uma lista de usuários com status OK")
  void must_find_all_users_with_status_OK() {
    given(service.findAll()).willReturn(List.of(user));
    given(mapper.map(any(), any())).willReturn(userDTO);

    var response = controller.findAll();

    assertNotNull(response);
    assertNotNull(response.getBody());
    assertFalse(response.getBody().isEmpty());
    assertEquals(OK, response.getStatusCode());
    assertEquals(1, response.getBody().size());
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(UserDTO.class, response.getBody().get(0).getClass());
    assertFields(response.getBody().get(0));
  }

  @Test
  @DisplayName("Deve criar um novo usuário e retornar status CREATED")
  void must_create_new_user_and_return_status_CREATED() {
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(ID).toUri();
    var res = ResponseEntity.created(location).build();

    given(service.create(any())).willReturn(user);

    var response = controller.create(userDTO);

    assertNotNull(response);
    assertNotNull(res);
    assertNull(response.getBody());
    assertEquals(CREATED, response.getStatusCode());
    assertEquals(response, res);
  }

  @Test
  @DisplayName("Deve atualizar um usuário e retornar status OK")
  void must_update_an_user_and_return_status_OK() {
    given(service.update(anyInt(), any())).willReturn(user);
    given(mapper.map(any(), any())).willReturn(userDTO);

    var response = controller.update(ID, userDTO);

    assertNotNull(response);
    assertNotNull(response.getBody());
    assertEquals(OK, response.getStatusCode());
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(UserDTO.class, response.getBody().getClass());
    assertFields(response.getBody());
  }

  private void assertFields(UserDTO actual) {
    assertEquals(user.getId(), actual.getId());
    assertEquals(user.getEmail(), actual.getEmail());
    assertEquals(user.getName(), actual.getName());
    assertEquals(user.getPassword(), actual.getPassword());
  }

  private void setupUsers() {
    this.user = User.builder()
            .id(ID)
            .name(NAME)
            .email(EMAIL)
            .password(PASSWORD)
            .build();

    this.userDTO = UserDTO.builder()
            .id(ID)
            .name(NAME)
            .email(EMAIL)
            .password(PASSWORD)
            .build();
  }

}