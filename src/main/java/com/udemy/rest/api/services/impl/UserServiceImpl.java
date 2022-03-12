package com.udemy.rest.api.services.impl;

import com.udemy.rest.api.model.User;
import com.udemy.rest.api.repositories.UserRepository;
import com.udemy.rest.api.services.UserService;
import com.udemy.rest.api.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository repository;

  public UserServiceImpl(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public User findById(Integer id) {
    return repository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
  }

}