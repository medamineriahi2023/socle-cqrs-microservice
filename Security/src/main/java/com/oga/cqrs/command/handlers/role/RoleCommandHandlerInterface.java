package com.oga.cqrs.command.handlers.role;


import com.oga.cqrs.command.commands.role.*;

public interface RoleCommandHandlerInterface {
  /**
   Handles the creation of a role with the given command.
   @param CreateRoleCommand the command for creating a role
   */

  void handle(CreateRoleCommand CreateRoleCommand) ;
  /**
   Handles the update of a role with the given command.
   @param UpdateRoleCommand the command for updating a role
   */

  void handle(UpdateRoleCommand UpdateRoleCommand) ;

  /**
   Handles the delete of a role with the given command.
   @param DeleteRoleCommand the command for delete a role
   */

  void handle(DeleteRoleCommand DeleteRoleCommand) ;
}
