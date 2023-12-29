package com.oga.cqrs.command.handlers.group;

import com.oga.cqrs.command.commands.group.CreateGroupCommand;
import com.oga.cqrs.command.commands.group.DeleteGroupCommand;
import com.oga.cqrs.command.commands.group.UpdateGroupCommand;
import com.oga.cqrs.command.domain.GroupAggregate;
import com.oga.cqrs.command.infrastructure.configuration.KeycloakConfig;
import com.oga.cqrs.command.infrastructure.configuration.KeycloakGeneric;
import com.oga.cqrs.command.infrastructure.exceptions.EntityAlreadyExists;
import com.oga.cqrsref.handlers.EventSourcingHandler;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.oga.cqrs.command.rest.constant.Constant.*;

@Slf4j
@Service
@EnableJpaRepositories("com.oga.cqrs.command.rest.repository")
@ComponentScan("com.oga.cqrsref.handlers")
public class GroupCommandHandlers implements GroupCommandHandlerInterface {

  private final EventSourcingHandler<GroupAggregate> eventSourcingHandler;
  RealmResource realmResource;

  private final KeycloakGeneric keycloakGeneric;
  @Autowired
  public GroupCommandHandlers(EventSourcingHandler<GroupAggregate> eventSourcingHandler, KeycloakConfig keycloakConfig, KeycloakGeneric keycloakGeneric) {
    this.eventSourcingHandler = eventSourcingHandler;
    this.keycloakGeneric=keycloakGeneric;
    this.realmResource = keycloakConfig.getInstance().realm(keycloakConfig.getRealm());
  }

  /**
   * handles the busness logic for creating a group
   * @param CreateGroupCommand the command for creating a group
   * @throws EntityAlreadyExists to check the existence of the  group created
   * realmResource contains the connexion to keycloak server
   * realmResource.groups() allows to manage groups in keycloak by calling the specific methods
   * if a group already exists , an exception is thrown
   * new GroupRepresentation() an instance of a representation of a group in keycloak
   * group.setName sets field name from value in CreateGroupCommand
   * realmResource.groups().add(group) calls the method add to create the group in keycloak
   * getIdFromLocationHeader a method that separate the url of the api called by keycloak to extract the id
   * groupId is setted in the field id of CreateGroupCommand
   * GroupAggregate permit to call the domain that handels the trigger of the event
   * eventSourcingHandler saves the object CreateGroupCommand in the event store
   */
  @Override
  public void handle(CreateGroupCommand CreateGroupCommand)  {
    {
      List<GroupRepresentation> groups = realmResource.groups().groups();
      if (groups.stream().anyMatch(group -> group.getName().equals(CreateGroupCommand.getGroup().getName()))) {
        throw new EntityAlreadyExists(GROUP_EXISTS);
      }
      GroupRepresentation group = new GroupRepresentation();
      group.setName(CreateGroupCommand.getGroup().getName());
      Response response = realmResource.groups().add(group);
      String groupId = keycloakGeneric.getIdFromLocationHeader(response.getHeaderString("Location"));
      CreateGroupCommand.getGroup().setId(groupId);
      GroupAggregate GroupAggregate = new GroupAggregate(CreateGroupCommand);
      eventSourcingHandler.save(GroupAggregate);}
  }
  /**
   * handles the busness logic for updating a group
   * @param UpdateGroupCommand the command for updating a group
   * realmResource contains the connexion to keycloak server
   * realmResource.groups() allows to manage groups in keycloak by calling the specific methods
   * UpdateGroupCommand.getGroup().getId() allows to search in keycloak a group with the same id
   * toRepresentation() a representation of a group in keycloak
   * group.setName sets new field name from value in UpdateGroupCommand
   * update(groupRepresentation)  calls the method update to  update the specific group in keycloak according to the id
   * GroupAggregate permit to call the domain that handels the trigger of the event
   * eventSourcingHandler saves the object UpdateGroupCommand in the event store
   */
  @Override
  public void handle(UpdateGroupCommand UpdateGroupCommand) {
    try{
      GroupRepresentation groupRepresentation = realmResource.groups().group(UpdateGroupCommand.getGroup().getId()).toRepresentation();
      groupRepresentation.setName(UpdateGroupCommand.getGroup().getName());
      realmResource.groups().group(UpdateGroupCommand.getGroup().getId()).update(groupRepresentation);
      GroupAggregate GroupAggregate = new GroupAggregate(UpdateGroupCommand);
      eventSourcingHandler.save(GroupAggregate);
    }
    catch (NotFoundException e)
    {
      throw new EntityNotFoundException(GROUP_NOT_FOUND);
    }
  }
  /**
   * handles the busness logic for deleting a group
   * @param DeleteGroupCommand the command for deleting a group
   * realmResource contains the connexion to keycloak server
   * realmResource.groups() allows to manage groups in keycloak by calling the specific methods
   * DeleteGroupCommand.getIdgroup() allows to search in keycloak a group with the same id
   * members() gives all users related to that specific group
   * if the group is associated with at least one user, an exception is thrown
   * if the group is not associated , it is deleted successfully
   * remove() a specific method to remove a group from keycloak server
   * GroupAggregate permit to call the domain that handels the trigger of the event
   * eventSourcingHandler saves the object DeleteGroupCommand in the event store
   */
  @Override
  public void handle(DeleteGroupCommand DeleteGroupCommand) {
    List<UserRepresentation> userRepresentations = realmResource
        .groups().group(DeleteGroupCommand.getIdgroup()).members();
    if(!userRepresentations.isEmpty())
    {
      throw new IllegalArgumentException(GROUP_USER_ASSOCIATION);
    }

    realmResource.groups().group(DeleteGroupCommand.getIdgroup()).remove();
    GroupAggregate GroupAggregate = new GroupAggregate(DeleteGroupCommand);

    eventSourcingHandler.save(GroupAggregate);
  }

}
