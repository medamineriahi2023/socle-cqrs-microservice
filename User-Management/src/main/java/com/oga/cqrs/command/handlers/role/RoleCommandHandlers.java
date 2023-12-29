package com.oga.cqrs.command.handlers.role;

import com.oga.cqrs.command.commands.role.*;
import com.oga.cqrs.command.domain.RoleAggregate;
import com.oga.cqrs.command.infrastructure.configuration.*;
import com.oga.cqrs.command.infrastructure.exceptions.EntityAlreadyExists;
import com.oga.cqrsref.handlers.EventSourcingHandler;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.oga.cqrs.command.rest.constant.Constant.*;

@Slf4j
@Service
@EnableJpaRepositories("com.oga.cqrs.command.rest.repository")
@ComponentScan("com.oga.cqrsref.handlers")
public class RoleCommandHandlers implements RoleCommandHandlerInterface{
  private final EventSourcingHandler<RoleAggregate> eventSourcingHandler;
  RealmResource realmResource;

  private final KeycloakGeneric keycloakGeneric;
  @Autowired
  public RoleCommandHandlers(EventSourcingHandler<RoleAggregate> eventSourcingHandler, KeycloakConfig keycloakConfig, KeycloakGeneric keycloakGeneric) {
    this.eventSourcingHandler = eventSourcingHandler;
    this.keycloakGeneric=keycloakGeneric;
    this.realmResource = keycloakConfig.getInstance().realm(keycloakConfig.getRealm());
  }
  /**
   * handles the busness logic for creating a role
   * @param CreateRoleCommand the command for creating a role
   * @throws EntityAlreadyExists to check the existence of the role created
   * realmResource contains the connexion to keycloak server
   * realmResource.roles() allows to manage roles in keycloak by calling the specific methods
   * if a role already exists , an exception is thrown
   * new RoleRepresentation() an instance of a representation of a role in keycloak
   * role.setName sets field name from value in CreateRoleCommand
   * role.setDescription sets field Description from value in CreateRoleCommand
   * create(role) calls the method add to create the role in keycloak
   * newroles.stream() used to search for the new user created in keycloak
   * CreateRoleCommand.getRole().setId(roles.get(0).getId()) used to get the id of the new role to set it in CreateRoleCommand
   * RoleAggregate permit to call the domain that handels the trigger of the event
   * eventSourcingHandler saves the object CreateRoleCommand in the event store
   */
  @Override
  public void handle(CreateRoleCommand CreateRoleCommand) {
    List<RoleRepresentation> existingRoles = realmResource.roles().list();
    if (existingRoles.stream().anyMatch(r -> CreateRoleCommand.getRole().getName().equals(r.getName()))) {
      throw new EntityAlreadyExists("role exists");
    }

    RoleRepresentation role = new RoleRepresentation();
    role.setName(CreateRoleCommand.getRole().getName());
    role.setDescription(CreateRoleCommand.getRole().getDescription());
    role.setClientRole(false);
    realmResource.roles().create(role);
    List<RoleRepresentation> newroles = realmResource.roles().list();
    List<RoleRepresentation>roles= newroles.stream().filter(r -> CreateRoleCommand.getRole().getName().equals(r.getName())).toList();
    CreateRoleCommand.getRole().setId(roles.get(0).getId());
    RoleAggregate RoleAggregate = new RoleAggregate(CreateRoleCommand);

    eventSourcingHandler.save(RoleAggregate);
  }
  /**
   * handles the busness logic for updating a role
   * @param UpdateRoleCommand the command for updating a role
   * @throws EntityNotFoundException to check the existence of the role to update
   * inside the try block:
   * new RoleRepresentation() an instance of a representation of a role in keycloak
   * updatedRole.setName sets field name from value in UpdateRoleCommand
   * updatedRole.setDescription sets field Description from value in UpdateRoleCommand
   * realmResource contains the connexion to keycloak server
   * realmResource.roles() allows to manage roles in keycloak by calling the specific methods
   * update(updatedRole) calls the method update to change the role in keycloak
   * RoleAggregate permit to call the domain that handels the trigger of the event
   * eventSourcingHandler saves the object CreateRoleCommand in the event store
   * inside the catch block:
   * an exception is thrown , EntityNotFoundException(GROUP_NOT_FOUND)
   */
  @Override
  public void handle(UpdateRoleCommand UpdateRoleCommand) {
    try {
      RoleRepresentation updatedRole = new RoleRepresentation();
      updatedRole.setId(UpdateRoleCommand.getRole().getId());
      updatedRole.setName(UpdateRoleCommand.getRole().getName());
      updatedRole.setDescription(UpdateRoleCommand.getRole().getDescription());
      RoleRepresentation roleRepresentation = realmResource.rolesById().getRole(UpdateRoleCommand.getRole().getId());
      realmResource.roles().get(roleRepresentation.getName()).update(updatedRole);
      RoleAggregate RoleAggregate = new RoleAggregate(UpdateRoleCommand);

      eventSourcingHandler.save(RoleAggregate);
    }
    catch (NotFoundException e)
    {
      throw new EntityNotFoundException(GROUP_NOT_FOUND);
    }
  }
  /**
   * handles the busness logic for deleting a role
   * @param DeleteRoleCommand the command for deleting a role
   * realmResource contains the connexion to keycloak server
   * realmResource.rolesById() allows to manage groups in keycloak by calling the specific methods
   * realmResource.users().list().stream() used to get all the users present in keyclaok
   * filter used to search users according to the specific role
   * roleMappingResource return roles related to the users
   * users.isEmpty() checks the state of a list
   * if the role is associated with at least one user, an exception is thrown
   * if the role is not associated with at least one user, it is deleted successfully
   * realmResource.groups()  used to get all the groups in keycloak
   * filter used to search groups according to the specific role
   * groups.isEmpty() checks the state of a list
   * if the role is associated with at least one group, an exception is thrown
   * if the role is not associated with at least one group, it is deleted successfully
   * remove() a specific method to remove a role from keycloak server
   * RoleAggregate permit to call the domain that handels the trigger of the event
   * eventSourcingHandler saves the object DeleteRoleCommand in the event store
   */
  @Override
  public void handle(DeleteRoleCommand DeleteRoleCommand) {
    RoleRepresentation roleRepresentation = realmResource.rolesById().getRole(DeleteRoleCommand.getIdrole());
    List<UserRepresentation> users = realmResource.users().list().stream().filter(u -> {
      UserResource userResource = realmResource.users().get(u.getId());
      RoleMappingResource roleMappingResource = userResource.roles();
      return roleMappingResource.realmLevel().listAll().stream().anyMatch(r -> r.getId().equals(DeleteRoleCommand.getIdrole()));
    }).toList();
    if (!users.isEmpty()) {
      throw new IllegalArgumentException(ROLE_USER_ASSOCIATION);
    }
    List<GroupRepresentation> groups = realmResource.groups().groups().stream().filter(g -> {
      GroupResource groupResource = realmResource.groups().group(g.getId());
      RoleMappingResource roleMappingResource = groupResource.roles();
      return roleMappingResource.realmLevel().listAll().stream().anyMatch(r -> r.getId().equals(DeleteRoleCommand.getIdrole()));
    }).toList();

    if (!groups.isEmpty()) {
      throw new IllegalArgumentException(ROLE_GROUP_ASSOCIATION);
    }
    realmResource.roles().get(roleRepresentation.getName()).remove();
    RoleAggregate RoleAggregate = new RoleAggregate(DeleteRoleCommand);

    eventSourcingHandler.save(RoleAggregate);
  }


}
