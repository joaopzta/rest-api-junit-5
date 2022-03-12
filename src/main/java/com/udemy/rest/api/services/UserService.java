package com.udemy.rest.api.services;

import com.udemy.rest.api.model.User;

import java.util.List;

public interface UserService {
  User findById(Integer id);
  List<User> findAll();
}
