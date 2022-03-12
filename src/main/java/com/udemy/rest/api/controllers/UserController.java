package com.udemy.rest.api.controllers;

import com.udemy.rest.api.model.dto.UserDTO;
import com.udemy.rest.api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService service;
  private final ModelMapper mapper;

  public UserController(UserService service, ModelMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @GetMapping
  public ResponseEntity<List<UserDTO>> findAll() {
    var users = service.findAll()
            .stream()
            .map(user -> mapper.map(user, UserDTO.class))
            .toList();
    return ResponseEntity.ok().body(users);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
    var user = service.findById(id);
    return ResponseEntity.ok().body(mapper.map(user, UserDTO.class));
  }

}
