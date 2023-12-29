package com.oga.cqrs.command.events.role;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrs.command.rest.dto.Role;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
 * RoleCreated  represents an event related to the creation of a role, It extends a class named BaseEvent.
 * RoleCreated  has two instance variables: identifier and role.
 * identifier : the identifier of the command related to RoleCreated
 * role : an object representing the role to be added
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class RoleCreated extends BaseEvent {
  private String identifier;
  private Role role;
}
