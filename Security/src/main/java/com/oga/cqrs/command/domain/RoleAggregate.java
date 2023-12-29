package com.oga.cqrs.command.domain;


import com.oga.cqrs.command.commands.role.*;
import com.oga.cqrs.command.events.role.*;
import com.oga.cqrs.command.rest.dto.Role;
import com.oga.cqrsref.domain.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleAggregate extends AggregateRoot {
  private String  id;
  private Role role;
  /**
   * Constructor for the role aggregate, taking a CreateRoleCommand as input.
   * This constructor raises a RoleCreated with the data from the command.
   * @param command The CreateRoleCommand
   */
  public RoleAggregate(CreateRoleCommand command) {
    raiseEvent(RoleCreated.builder()
        .identifier(command.getId())
        .role(command.getRole())
        .build());
  }
  /**
   * Constructor for the role aggregate, taking a UpdateRoleCommand as input.
   * This constructor raises a RoleUpdated with the data from the command.
   * @param command The UpdateRoleCommand
   */
  public RoleAggregate(UpdateRoleCommand command) {
    raiseEvent(RoleUpdated.builder()
        .identifier(command.getId())
        .role(command.getRole())
        .build());
  }
  /**
   * Constructor for the role aggregate, taking a DeleteRoleCommand as input.
   * This constructor raises a RoleDeleted with the data from the command.
   * @param command The DeleteRoleCommand
   */
  public RoleAggregate(DeleteRoleCommand command) {
    raiseEvent(RoleDeleted.builder()
        .identifier(command.getId())
        .idrole(command.getIdrole())
        .build());
  }
  /**
   * Applies a RoleDeleted to the role aggregate, updating its state.
   * This method sets the id of the role to the one in the event.
   * @param event The RoleDeleted
   */
  public void apply (RoleDeleted event)
  {
    this.id=event.getIdentifier();

  }
  /**
   * Applies a RoleCreated to the group aggregate, updating its state.
   * This method sets the id of the role to the one in the event.
   * @param event The RoleCreated
   */
  public void apply (RoleCreated event)
  {
    this.id=event.getIdentifier();

  }

  /**
   * Applies a RoleUpdated to the group aggregate, updating its state.
   * This method sets the id of the role to the one in the event.
   * @param event The RoleUpdated
   */
  public void apply (RoleUpdated event)
  {
    this.id=event.getIdentifier();

  }

}
