package com.oga.cqrs.command.events.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
 * LogoutRequested  represents an event related to log out of a specific user, It extends a class named BaseEvent.
 * LogoutRequested  has two instance variables: identifier and iduser.
 * identifier : the identifier of the command related to LogoutRequested
 * iduser : the identifier of the user log out
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize

public class LogoutRequested extends BaseEvent {

  private String identifier;
  private String iduser;
}
