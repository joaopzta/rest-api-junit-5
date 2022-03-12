package com.udemy.rest.api.controllers;

import com.udemy.rest.api.model.User;
import com.udemy.rest.api.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> findById(@PathVariable Integer id) {
    return ResponseEntity.ok().body(service.findById(id));
  }

}
