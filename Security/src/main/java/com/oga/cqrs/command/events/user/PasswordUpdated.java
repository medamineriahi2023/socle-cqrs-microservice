package com.oga.cqrs.command.events.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrs.command.rest.dto.Login;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
 * PasswordUpdated  represents an event related to the update of a password of a specific user, It extends a class named BaseEvent.
 * PasswordUpdated  has two instance variables: identifier and login.
 * identifier : the identifier of the command related to PasswordUpdated
 * login : an object representing the password of a user to be updated
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class PasswordUpdated extends BaseEvent {
  private String identifier;
  private Login login;
}

