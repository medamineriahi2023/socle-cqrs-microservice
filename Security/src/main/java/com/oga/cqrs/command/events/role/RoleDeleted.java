package com.oga.cqrs.command.events.role;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
 * RoleDeleted  represents an event related to the remove of a specific role, It extends a class named BaseEvent.
 * RoleDeleted  has two instance variables: identifier and idrole.
 * identifier : the identifier of the command related to RoleDeleted
 * idrole : the identifier of the role to be deleted
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class RoleDeleted extends BaseEvent {
  private String identifier;
  private String idrole;
}
