package com.oga.cqrs.command.events.group;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
 * GroupDeleted  represents an event related to the remove of a specific group, It extends a class named BaseEvent.
 * GroupDeleted  has two instance variables: identifier and idgroup.
 * identifier : the identifier of the command related to GroupDeleted
 * idgroup : the identifier of the group to be deleted
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class GroupDeleted extends BaseEvent {
  private String identifier;
  private String idgroup;
}
