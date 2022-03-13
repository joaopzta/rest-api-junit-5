package com.udemy.rest.api.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StandartError {

  private LocalDateTime timestamp;
  private Integer statusCode;
  private String error;
  private String path;

}
