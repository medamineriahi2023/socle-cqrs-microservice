package com.oga.cqrs.command.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenRequestLogin {

  private String username;
  private String password;
  private String Token;
}
