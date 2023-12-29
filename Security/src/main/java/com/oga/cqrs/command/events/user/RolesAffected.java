package com.oga.cqrs.command.events.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
/**
 * RolesAffected  represents an event related to the affection of roles to user, It extends a class named BaseEvent.
 * RolesAffected  has three instance variables: identifier, rolesIds and userId.
 * identifier : the identifier of the command related to RolesAffected
 * userId : an object representing the user's id to be added
 * rolesIds: array of roles id to affect to user
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize

public class RolesAffected extends BaseEvent {
  private String identifier;
  private String userId;
  private List<String> roleIds;
}
