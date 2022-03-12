package com.udemy.rest.api.services.impl;

import com.udemy.rest.api.model.User;
import com.udemy.rest.api.model.dto.UserDTO;
import com.udemy.rest.api.repositories.UserRepository;
import com.udemy.rest.api.services.UserService;
import com.udemy.rest.api.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

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
    return repository.save(mapper.map(user, User.class));
  }

}