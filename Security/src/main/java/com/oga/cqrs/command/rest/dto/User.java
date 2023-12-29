package com.oga.cqrs.command.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User  {
  private String	      id;

  @NotBlank(message = "firstName is required")
  private String	      firstName;
  @NotBlank(message = "lastName is required")
  private String	      lastName;
  @NotBlank(message = "email is required")
  @Email(message = "email is not valid")
  private String	      email;
  @NotBlank(message = "userName is required")
  private String	      userName;

  private String	      password;

  private String	      phone;

  private Boolean isActive;
}
