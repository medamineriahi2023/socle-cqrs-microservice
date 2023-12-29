package com.oga.cqrs.command.events.role;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrs.command.rest.dto.Role;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
 * RoleUpdated  represents an event related to the update of a role, It extends a class named BaseEvent.
 * RoleUpdated  has two instance variables: identifier and role.
 * identifier : the identifier of the command related to RoleUpdated
 * role : an object representing the role to be updated
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class RoleUpdated extends BaseEvent {
  private String identifier;
  private Role role;
}
