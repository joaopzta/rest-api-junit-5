package com.udemy.rest.api.services;

import com.udemy.rest.api.model.User;
import com.udemy.rest.api.model.dto.UserDTO;
import com.udemy.rest.api.repositories.UserRepository;
import com.udemy.rest.api.services.exceptions.DataIntegratyViolationException;
import com.udemy.rest.api.services.exceptions.ObjectNotFoundException;
import com.udemy.rest.api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Garante o funcionamento da classe UserService")
class UserServiceTest {

  private static final Integer ID                  = 1;
  private static final String NAME                 = "Joao";
  private static final String EMAIL                = "joao@gmail.com";
  private static final String PASSWORD             = "1234";
  private static final String OBJECT_NOT_FOUND     = "Objeto não encontrado!";
  private static final String EMAIL_ALREADY_EXISTS = "O email informado já existe no sistema";

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
    assertFields(response);
  }

  @Test
  @DisplayName("Deve lançar exceção de objeto nao encontrado")
  void must_throw_exception_when_id_doest_not_exists() {
    doThrow(new ObjectNotFoundException(OBJECT_NOT_FOUND)).when(userRepository).findById(anyInt());

    var exFind = assertThrows(ObjectNotFoundException.class, () -> service.findById(ID));
    var exDelete = assertThrows(ObjectNotFoundException.class, () -> service.delete(ID));

    assertExceptions(ObjectNotFoundException.class, exFind, OBJECT_NOT_FOUND);
    assertExceptions(ObjectNotFoundException.class, exDelete, OBJECT_NOT_FOUND);
  }

  @Test
  @DisplayName("Deve listar todos os usuários")
  void must_list_all_users() {
    given(userRepository.findAll()).willReturn(List.of(user));

    var response = service.findAll();

    assertNotNull(response);
    assertFalse(response.isEmpty());
    assertEquals(1, response.size());
    assertEquals(User.class, response.get(0).getClass());
    assertFields(response.get(0));
  }

  @Test
  @DisplayName("Deve criar um novo usuário no banco")
  void must_create_new_user() {
    given(userRepository.save(any())).willReturn(user);

    var response = service.create(dto);

    assertNotNull(response);
    assertEquals(User.class, response.getClass());
    assertFields(response);
  }

  @Test
  @DisplayName("Deve atualizar um usuário já existente")
  void must_update_an_user() {
    given(userRepository.save(any())).willReturn(user);

    var response = service.update(ID, dto);

    assertNotNull(response);
    assertEquals(User.class, response.getClass());
    assertFields(response);
  }

  @Test
  @DisplayName("Deve deletar um usuário no banco")
  void must_delete_an_user() {
    given(userRepository.findById(anyInt())).willReturn(userOpt);
    doNothing().when(userRepository).delete(any());

    service.delete(ID);

    verify(userRepository).findById(anyInt());
    verify(userRepository).delete(any(User.class));
  }

  @Test
  @DisplayName("Deve lançar exceção quando o email já existir")
  void must_throw_exception_when_email_already_exists() {
    given(userRepository.findByEmail(anyString())).willReturn(userOpt);

    dto.setId(2);
    var exCreate =
            assertThrows(DataIntegratyViolationException.class, () -> service.create(dto));

    userOpt.ifPresent(user -> user.setId(2));
    var exUpdate =
            assertThrows(DataIntegratyViolationException.class, () -> service.update(ID, dto));

    assertExceptions(DataIntegratyViolationException.class, exCreate, EMAIL_ALREADY_EXISTS);
    assertExceptions(DataIntegratyViolationException.class, exUpdate, EMAIL_ALREADY_EXISTS);
  }

  private <T> void assertExceptions(Class<T> type, Exception ex, String msg) {
    assertEquals(type, ex.getClass());
    assertEquals(msg, ex.getMessage());
  }

  private void assertFields(User actual) {
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

    this.userOpt = Optional.of(user);

    this.dto = UserDTO.builder()
            .id(ID)
            .name(NAME)
            .email(EMAIL)
            .password(PASSWORD)
            .build();
  }
}