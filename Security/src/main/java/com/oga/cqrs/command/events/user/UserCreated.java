package com.oga.cqrs.command.events.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrs.command.rest.dto.User;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * UserCreated  represents an event related to the creation of a user, It extends a class named BaseEvent.
 * UserCreated  has two instance variables: identifier and user.
 * identifier : the identifier of the command related to UserCreated
 * user : an object representing the user to be added
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class UserCreated extends BaseEvent {

private String identifier;
  private User user;
}
