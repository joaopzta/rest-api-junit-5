package com.udemy.rest.api.services;

import com.udemy.rest.api.model.User;
import com.udemy.rest.api.model.dto.UserDTO;
import com.udemy.rest.api.repositories.UserRepository;
import com.udemy.rest.api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Garante o funcionamento da classe UserService")
class UserServiceTest {

  public static final Integer ID      = 1;
  public static final String NAME     = "Joao";
  public static final String EMAIL    = "joao@gmail.com";
  public static final String PASSWORD = "1234";

  @Mock
  private UserRepository userRepository;

  @Mock
  private ModelMapper mapper;

  private UserServiceImpl service;

  private User user;
  private UserDTO dto;

  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  private Optional<User> userOpt;

  @BeforeEach
  void setUp() {
    service = new UserServiceImpl(userRepository, mapper);
    setupUsers();
  }

  @Test
  @DisplayName("Deve retornar um usuário pelo ID")
  void must_find_user_by_id() {
    given(userRepository.findById(anyInt())).willReturn(userOpt);

    var response = service.findById(ID);

    assertNotNull(response);
    assertEquals(User.class, response.getClass());
    assertEquals(ID, response.getId());
    assertEquals(NAME, response.getName());
    assertEquals(EMAIL, response.getEmail());
    assertEquals(PASSWORD, response.getPassword());

  }

  @Test
  void findAll() {
  }

  @Test
  void create() {
  }

  @Test
  void update() {
  }

  @Test
  void delete() {
  }

  private void setupUsers() {
    this.user = User.builder()
            .id(ID)
            .name(NAME)
            .email(EMAIL)
            .password(PASSWORD)
            .build();

    this.userOpt = Optional.of(user);

    this.dto = UserDTO.builder()
            .id(ID)
            .name(NAME)
            .email(EMAIL)
            .password(PASSWORD)
            .build();
  }
}