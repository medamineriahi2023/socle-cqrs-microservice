package com.oga.cqrs.command.handlers.group;

import com.oga.cqrs.command.commands.group.*;


public interface GroupCommandHandlerInterface {
  /**
   Handles the creation of a group with the given command.
   @param CreateGroupCommand the command for creating a group
   */

  void handle(CreateGroupCommand CreateGroupCommand) ;

  /**
   Handles the creation of a group with the given command.
   @param UpdateGroupCommand the command for creating a group
   */

  void handle(UpdateGroupCommand UpdateGroupCommand) ;

  /**
   Handles  delete of a group with the given command.
   @param DeleteGroupCommand the command for deleting a group
   */

  void handle(DeleteGroupCommand DeleteGroupCommand) ;
}
