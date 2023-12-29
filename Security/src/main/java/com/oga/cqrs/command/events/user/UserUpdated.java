package com.oga.cqrs.command.events.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrs.command.rest.dto.User;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
 * UserUpdated  represents an event related to the update of a user, It extends a class named BaseEvent.
 * UserUpdated  has two instance variables: identifier and user.
 * identifier : the identifier of the command related to UserUpdated
 * user : an object representing the user to be updated
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class UserUpdated extends BaseEvent {
 private String identifier;
  private User user;
}
