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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Garante o funcionamento da classe UserController")
class UserControllerTest {

  private static final Integer ID                  = 1;
  private static final String NAME                 = "Joao";
  private static final String EMAIL                = "joao@gmail.com";
  private static final String PASSWORD             = "1234";

  private UserController controller;

  @Mock
  private UserServiceImpl service;

  @Mock
  private ModelMapper mapper;

  private User user;
  private UserDTO userDTO;

  @BeforeEach
  void setUp() {
    controller = new UserController(service, mapper);
    setupUsers();
  }

  @Test
  void must_find_user_by_id() {
    given(service.findById(anyInt())).willReturn(user);
    given(mapper.map(any(), any())).willReturn(userDTO);

    var response = controller.findById(ID);

    assertNotNull(response);
    assertNotNull(response.getBody());
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