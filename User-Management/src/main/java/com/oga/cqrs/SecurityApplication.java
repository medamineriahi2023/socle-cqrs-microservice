package com.oga.cqrs;


import com.oga.cqrs.command.commands.group.*;
import com.oga.cqrs.command.commands.organization.CreateOrganizationCommand;
import com.oga.cqrs.command.commands.role.CreateRoleCommand;
import com.oga.cqrs.command.commands.role.DeleteRoleCommand;
import com.oga.cqrs.command.commands.role.UpdateRoleCommand;
import com.oga.cqrs.command.commands.user.*;
import com.oga.cqrs.command.handlers.group.GroupCommandHandlers;
import com.oga.cqrs.command.handlers.organization.OrganizationCommandHandlers;
import com.oga.cqrs.command.handlers.role.RoleCommandHandlers;
import com.oga.cqrs.command.handlers.user.UserCommandHandlers;
import com.oga.cqrs.query.handlers.group.IGroupQueryHandler;
import com.oga.cqrs.query.handlers.organization.IOrganizationQueryHandler;
import com.oga.cqrs.query.handlers.role.IRoleQueryHandler;
import com.oga.cqrs.query.handlers.user.IUserQueryHandler;
import com.oga.cqrs.query.queries.group.*;
import com.oga.cqrs.query.queries.organizations.FindAllOrganizations;
import com.oga.cqrs.query.queries.role.FindAllRoles;
import com.oga.cqrs.query.queries.role.FindRoleById;
import com.oga.cqrs.query.queries.user.*;
import com.oga.cqrsref.infrastructure.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.EnableKafka;

import javax.annotation.PostConstruct;


@SpringBootApplication
@EnableEurekaClient
@EnableKafka
public class SecurityApplication {


  private final UserCommandHandlers usercommandHandler;
  private final GroupCommandHandlers groupCommandHandlers;

  private final OrganizationCommandHandlers organizationCommandHandlers;
  private final RoleCommandHandlers roleCommandHandlers;
  private final IUserQueryHandler userQueryHandler;
  private final IGroupQueryHandler groupQueryHandler;
  private final IRoleQueryHandler RoleQueryHandler;
  private final IOrganizationQueryHandler organizationQueryHandler;


  @Autowired
  private CommandDispatcher commandDispatcher;
  @Autowired
  private  QueryDispatcher queryDispatcher;

  @Autowired
  public SecurityApplication(UserCommandHandlers usercommandHandler, OrganizationCommandHandlers organizationCommandHandlers, IUserQueryHandler userQueryHandler, GroupCommandHandlers groupCommandHandlers, IGroupQueryHandler groupQueryHandler, RoleCommandHandlers roleCommandHandlers , IRoleQueryHandler RoleQueryHandler, IOrganizationQueryHandler organizationQueryHandler) {
    this.usercommandHandler = usercommandHandler;
      this.organizationCommandHandlers = organizationCommandHandlers;
      this.groupCommandHandlers=groupCommandHandlers;
    this.userQueryHandler = userQueryHandler;
    this.groupQueryHandler=groupQueryHandler;
    this.roleCommandHandlers=roleCommandHandlers;
    this.RoleQueryHandler=RoleQueryHandler;

      this.organizationQueryHandler = organizationQueryHandler;
  }


  @PostConstruct
  public void registerHandlers() {

    commandDispatcher.registerHandler(CreateUserCommand.class, usercommandHandler::handle);
    commandDispatcher.registerHandler(UpdatePasswordCommand.class, usercommandHandler::handle);
    commandDispatcher.registerHandler(UpdateUserCommand.class, usercommandHandler::handle);
    commandDispatcher.registerHandler(DeleteUserCommand.class, usercommandHandler::handle);
    commandDispatcher.registerHandler(CreateUserByAdmin.class, usercommandHandler::handle);
    queryDispatcher.registerHandler(FindAllUsers.class, userQueryHandler::handle);
    queryDispatcher.registerHandler(FindUserById.class, userQueryHandler::handle);
    commandDispatcher.registerHandler(CreateTokenRequestCommand.class, usercommandHandler::handle);
    commandDispatcher.registerHandler(CreateLogoutRequestCommand.class, usercommandHandler::handle);
    commandDispatcher.registerHandler(ForgetPasswordCommand.class, usercommandHandler::handle);
    commandDispatcher.registerHandler(AffectRolesCommand.class, usercommandHandler::handle);


    commandDispatcher.registerHandler(CreateGroupCommand.class, groupCommandHandlers::handle);
    commandDispatcher.registerHandler(UpdateGroupCommand.class, groupCommandHandlers::handle);
    commandDispatcher.registerHandler(DeleteGroupCommand.class, groupCommandHandlers::handle);
    commandDispatcher.registerHandler(CreateOrganizationCommand.class, organizationCommandHandlers::handle);
    queryDispatcher.registerHandler(FindGroupById.class, groupQueryHandler::handle);
    queryDispatcher.registerHandler(FindAllGroups.class, groupQueryHandler::handle);

    commandDispatcher.registerHandler(CreateRoleCommand.class, roleCommandHandlers::handle);
    commandDispatcher.registerHandler(UpdateRoleCommand.class, roleCommandHandlers::handle);
    commandDispatcher.registerHandler(DeleteRoleCommand.class, roleCommandHandlers::handle);
    queryDispatcher.registerHandler(FindAllRoles.class, RoleQueryHandler::handle);
    queryDispatcher.registerHandler(FindRoleById.class, RoleQueryHandler::handle);
    queryDispatcher.registerHandler(FindAllOrganizations.class, organizationQueryHandler::handle);
  }


  public static void main(String[] args) {
    SpringApplication.run(SecurityApplication.class, args);
  }


}

