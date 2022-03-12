package com.udemy.rest.api.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class StandartError {

  private LocalDateTime timestamp;
  private Integer statusCode;
  private String error;
  private String path;

}
