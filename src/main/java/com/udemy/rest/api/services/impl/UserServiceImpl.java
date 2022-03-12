package com.udemy.rest.api.services.impl;

import com.udemy.rest.api.model.User;
import com.udemy.rest.api.model.dto.UserDTO;
import com.udemy.rest.api.repositories.UserRepository;
import com.udemy.rest.api.services.UserService;
import com.udemy.rest.api.services.exceptions.DataIntegratyViolationException;
import com.udemy.rest.api.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private static final String EMAIL_ALREADY_EXISTS = "O email informado já existe no sistema";

  private final UserRepository repository;
  private final ModelMapper mapper;

  public UserServiceImpl(UserRepository repository, ModelMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public User findById(Integer id) {
    return repository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
  }

  public List<User> findAll() {
    return repository.findAll();
  }

  @Override
  public User create(UserDTO user) {
    findByEmail(user);
    return repository.save(mapper.map(user, User.class));
  }

  @Override
  public User update(Integer id, UserDTO dto) {
    dto.setId(id);
    findByEmail(dto);
    return repository.save(mapper.map(dto, User.class));
  }

  @Override
  public void delete(Integer id) {
    var user = this.findById(id);
    repository.delete(user);
  }

  private void findByEmail(UserDTO dto) {
    Optional<User> user = repository.findByEmail(dto.getEmail());
    if (user.isPresent() && !user.get().getId().equals(dto.getId()))
      throw new DataIntegratyViolationException(EMAIL_ALREADY_EXISTS);
  }

}