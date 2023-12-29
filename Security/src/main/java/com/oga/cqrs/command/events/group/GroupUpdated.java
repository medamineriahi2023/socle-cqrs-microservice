package com.oga.cqrs.command.events.group;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrs.command.rest.dto.Group;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
 * GroupUpdated  represents an event related to the update of a group, It extends a class named BaseEvent.
 * GroupUpdated  has two instance variables: identifier and group.
 * identifier : the identifier of the command related to GroupUpdated
 * group : an object representing the group to be updated
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class GroupUpdated extends BaseEvent {
  private String identifier;
  private Group group;
}
