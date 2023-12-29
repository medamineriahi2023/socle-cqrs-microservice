package com.oga.cqrs.command.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

  private String	      id;

  @NotBlank(message = "name is required")
  private String	     name;


  private String	     description;


}
