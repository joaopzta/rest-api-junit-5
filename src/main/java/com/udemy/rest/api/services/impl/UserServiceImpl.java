package com.udemy.rest.api.services.impl;

import com.udemy.rest.api.model.User;
import com.udemy.rest.api.repositories.UserRepository;
import com.udemy.rest.api.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository repository;

  public UserServiceImpl(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public User findById(Integer id) {
    Optional<User> user = repository.findById(id);
    return user.orElse(null);
  }

}