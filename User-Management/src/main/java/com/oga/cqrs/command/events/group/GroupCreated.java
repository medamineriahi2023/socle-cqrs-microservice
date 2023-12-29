package com.oga.cqrs.command.events.group;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrs.command.rest.dto.Group;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
 * GroupCreated  represents an event related to the creation of a group, It extends a class named BaseEvent.
 * GroupCreated  has two instance variables: identifier and group.
 * identifier : the identifier of the command related to GroupCreated
 * group : an object representing the group to be added
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class GroupCreated extends BaseEvent {
  private String identifier;
  private Group group;
}
