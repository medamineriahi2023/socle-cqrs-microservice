package com.oga.cqrs.command.infrastructure.exceptions;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityAlreadyExists extends IllegalArgumentException {
private String message;

}
