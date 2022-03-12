package com.udemy.rest.api.services;

import com.udemy.rest.api.model.User;

public interface UserService {
  User findById(Integer id);
}
