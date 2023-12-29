package com.oga.cqrs.command.events.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
 * UserDeleted  represents an event related to the remove of a specific user, It extends a class named BaseEvent.
 * UserDeleted  has two instance variables: identifier and iduser.
 * identifier : the identifier of the command related to UserDeleted
 * iduser : the identifier of the user to be deleted
 */


@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class UserDeleted extends BaseEvent {
  private String identifier;
  private String iduser;
}
