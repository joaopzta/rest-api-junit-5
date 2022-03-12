package com.udemy.rest.api.services;

import com.udemy.rest.api.model.User;
import com.udemy.rest.api.model.dto.UserDTO;

import java.util.List;

public interface UserService {
  User findById(Integer id);
  List<User> findAll();
  User create(UserDTO user);
  User update(Integer id, UserDTO dto);
}
