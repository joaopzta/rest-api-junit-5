package com.udemy.rest.api.controllers;

import com.udemy.rest.api.model.User;
import com.udemy.rest.api.model.dto.UserDTO;
import com.udemy.rest.api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

  public static final String ID = "/{id}";

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

  @GetMapping(ID)
  public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
    var user = service.findById(id);
    return ResponseEntity.ok().body(mapper.map(user, UserDTO.class));
  }

  @PostMapping
  public ResponseEntity<UserDTO> create(@RequestBody UserDTO user) {
    var newUser = service.create(user);
    var uri = getUriFromCurrentRequest(newUser);
    return ResponseEntity.created(uri).build();
  }

  @PutMapping(ID)
  public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO dto) {
    var userUpdated = service.update(id, dto);
    return ResponseEntity.ok().body(mapper.map(userUpdated, UserDTO.class));
  }

  @DeleteMapping(ID)
  public ResponseEntity<UserDTO> delete(@PathVariable Integer id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  private URI getUriFromCurrentRequest(User user) {
    return ServletUriComponentsBuilder.fromCurrentRequest()
            .path(ID)
            .buildAndExpand(user.getId())
            .toUri();
  }

}
