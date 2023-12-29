package com.oga.cqrs.command.domain;

import com.oga.cqrs.command.commands.group.*;
import com.oga.cqrs.command.events.group.*;
import com.oga.cqrs.command.rest.dto.Group;
import com.oga.cqrsref.domain.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupAggregate extends AggregateRoot {
  private String  id;
  private Group group;
  /**
   * Constructor for the group aggregate, taking a CreateGroupCommand as input.
   * This constructor raises a GroupCreated with the data from the command.
   * @param command The CreateGroupCommand
   */
  public GroupAggregate(CreateGroupCommand command) {
    raiseEvent(GroupCreated.builder()
        .identifier(command.getId())
        .group(command.getGroup())
        .build());
  }
  /**
   * Constructor for the group aggregate, taking a UpdateGroupCoammand as input.
   * This constructor raises a GroupUpdated with the data from the command.
   * @param command The UpdateGroupCoammand
   */
  public GroupAggregate(UpdateGroupCommand command) {
    raiseEvent(GroupUpdated.builder()
        .identifier(command.getId())
        .group(command.getGroup())
        .build());
  }
  /**
   * Constructor for the group aggregate, taking a DeleteGroupCommand as input.
   * This constructor raises a GroupDeleted with the data from the command.
   * @param command The DeleteGroupCommand
   */
  public GroupAggregate(DeleteGroupCommand command) {
    raiseEvent(GroupDeleted.builder()
        .identifier(command.getId())
        .idgroup(command.getIdgroup())
        .build());
  }
  /**
   * Applies a GroupCreated to the group aggregate, updating its state.
   * This method sets the id of the group to the one in the event.
   * @param event The GroupCreated
   */
  public void apply (GroupCreated event)
  {
    this.id=event.getIdentifier();

  }
  /**
   * Applies a GroupUpdated to the group aggregate, updating its state.
   * This method sets the id of the group to the one in the event.
   * @param event The GroupUpdated
   */
  public void apply (GroupUpdated event)
  {
    this.id=event.getIdentifier();

  }
  /**
   * Applies a DeleteUserEvent to the group aggregate, updating its state.
   * This method sets the id of the group to the one in the event.
   * @param event The GroupDeleted
   */
  public void apply (GroupDeleted event)
  {
    this.id=event.getIdentifier();

  }
}
