package com.udemy.rest.api.config;

import com.udemy.rest.api.model.User;
import com.udemy.rest.api.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

  private final UserRepository repository;

  public LocalConfig(UserRepository repository) {
    this.repository = repository;
  }

  @Bean
  public void startDB() {
    repository.saveAll(getUsers());
  }

  private List<User> getUsers() {
    User user1 = new User(null, "Joao", "joao@gmail.com", "1234");
    User user2 = new User(null, "Pedro", "pedro@gmail.com", "4321");
    return List.of(user1, user2);
  }

}
