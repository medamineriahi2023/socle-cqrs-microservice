package com.oga.cqrs.command.handlers.organization;

import com.oga.cqrs.command.commands.group.CreateGroupCommand;
import com.oga.cqrs.command.commands.group.DeleteGroupCommand;
import com.oga.cqrs.command.commands.group.UpdateGroupCommand;
import com.oga.cqrs.command.commands.organization.CreateOrganizationCommand;


public interface OrganizationCommandHandlerInterface {

  void handle(CreateOrganizationCommand createOrganizationCommand) ;
}
