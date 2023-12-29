package com.oga.cqrs.command.handlers.user;

import com.oga.cqrs.command.commands.user.*;
import com.oga.cqrs.command.domain.UserAggregate;
import com.oga.cqrs.command.infrastructure.configuration.KeycloakConfig;
import com.oga.cqrs.command.infrastructure.configuration.KeycloakGeneric;
import com.oga.cqrs.command.infrastructure.exceptions.EntityAlreadyExists;
import com.oga.cqrs.command.rest.dto.User;
import com.oga.cqrsref.handlers.EventSourcingHandler;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.*;

import static com.oga.cqrs.command.infrastructure.configuration.KeycloakGeneric.createPasswordCredentials;
import static com.oga.cqrs.command.rest.constant.Constant.*;

/**
 This class represents the implementation of the CommandHandlerInterface that handles
 incoming commands .
 */
@Slf4j
@Service
@EnableJpaRepositories("com.oga.cqrs.command.rest.repository")
@ComponentScan("com.oga.cqrsref.handlers")
public class UserCommandHandlers implements UserCommandHandlerInterface {

  private final EventSourcingHandler<UserAggregate> eventSourcingHandler;
  RealmResource realmResource;

  private final KeycloakGeneric keycloakGeneric;


  private final KeycloakConfig keycloakConfig;
  @Autowired
  public UserCommandHandlers(EventSourcingHandler<UserAggregate> eventSourcingHandler,KeycloakConfig keycloakConfig, KeycloakGeneric keycloakGeneric) {
    this.eventSourcingHandler = eventSourcingHandler;
    this.keycloakGeneric=keycloakGeneric;
    this.realmResource = keycloakConfig.getInstance().realm(keycloakConfig.getRealm());
    this.keycloakConfig=keycloakConfig;
  }


  /**
   * handles the busness logic for creating a user
   * @param createUserCommand the command for creating a user
   * @throws EntityAlreadyExists to check the existence of the user being created
   * this method calls another one addUserToKeycloak to avoid redundancy to permit to add a new user to keycloak
   * userid is the return of addUserToKeycloak that gives the id of the user created in keycloak
   * getIdFromLocationHeader a method that separate the url of the api called by keycloak to extract the id
   * userid is setted in the field id of createUserCommand
   * userAggregate permit to call the domain that handels the trigger of the event
   * eventSourcingHandler saves the object createUserCommand in the event store
   */
  @Override
  public void handle(CreateUserCommand createUserCommand) throws EntityAlreadyExists {

    Response response = addUserToKeycloak(createUserCommand.getUser());
    String userid = keycloakGeneric.getIdFromLocationHeader(response.getHeaderString("Location"));
    createUserCommand.getUser().setId(userid);
    UserAggregate userAggregate = new UserAggregate(createUserCommand);

    eventSourcingHandler.save(userAggregate);


  }

  /**
   * handles the busness logic for updating a specific user
   * @param updateUserCommand the command for updating a user
   * userrepresentation contains the return of the specific user according to the given id in updateUserCommand
   * attributes allows to add other attributes to user in keycloak that are not present by default
   * setters are applied to userrepresentation according to fields that are requested to be updated
   * realmResource contains the connexion to keycloak server
   * realmResource.users() allows to manage users in keycloak by calling the specific user to reform
   * update(userrepresentation) a method by default that takes the user updated to synchronise modification in keycloak
   * userAggregate permit to call the domain that handels the trigger of the event
   * eventSourcingHandler saves the object updateUserCommand in the event store
   */
  @Override
  public void handle(UpdateUserCommand updateUserCommand) {

    UserRepresentation userrepresentation = realmResource.users().get(updateUserCommand.getUser().getId()).toRepresentation();
    Map<String, List<String>> attributes = userrepresentation.getAttributes();
    if (attributes == null) {
      attributes = new HashMap<>();
    }
    userrepresentation.setUsername(updateUserCommand.getUser().getUserName());
    userrepresentation.setEmail(updateUserCommand.getUser().getEmail());
    userrepresentation.setFirstName(updateUserCommand.getUser().getFirstName());
    userrepresentation.setLastName(updateUserCommand.getUser().getLastName());
    attributes.put(PHONE_NUMBER, Collections.singletonList(updateUserCommand.getUser().getPhone()));
    userrepresentation.setAttributes(attributes);
    realmResource.users().get(updateUserCommand.getUser().getId()).update(userrepresentation);

    UserAggregate userAggregate = new UserAggregate(updateUserCommand);

    eventSourcingHandler.save(userAggregate);

  }

  /**
   * handles the busness logic for deleting a specific user
   * @param deleteUserCommand the command for deleting a user
   * realmResource.users() allows to manage users in keycloak by calling the specific user to reform
   * remove() a method by default that deletes the specific user in keycloak
   * userAggregate permit to call the domain that handels the trigger of the event
   * eventSourcingHandler saves the object deleteUserCommand in the event store
   */
  @Override
  public void handle(DeleteUserCommand deleteUserCommand) {
    realmResource.users().get(deleteUserCommand.getIduser()).remove();
    UserAggregate userAggregate = new UserAggregate(deleteUserCommand);
    eventSourcingHandler.save(userAggregate);

  }

  /**
   * handles the busness logic for update a user's password
   * @param UpdatePasswordCommand the command for updating a user's password
   * userrepresentation contains the return of the specific user according to the given id in UpdatePasswordCommand
   * createPasswordCredentials is a call to a method that generates a password credential representation based on a password obtained from UpdatePasswordCommand
   * realmResource contains the connexion to keycloak server
   * realmResource.users() allows to manage users in keycloak by calling the specific user to reform
   * update(userrepresentation) a method by default that takes the password to update to synchronise modification in keycloak
   * userAggregate permit to call the domain that handels the trigger of the event
   * eventSourcingHandler saves the object UpdatePasswordCommand in the event store
   */
  @Override
  public void handle(UpdatePasswordCommand UpdatePasswordCommand) {
    UserRepresentation userrepresentation = realmResource.users().get(UpdatePasswordCommand.getLogin().getIduser()).toRepresentation();
    CredentialRepresentation credentialRepresentation = createPasswordCredentials(UpdatePasswordCommand.getLogin().getPassword());
    userrepresentation.setCredentials(Collections.singletonList(credentialRepresentation));
    realmResource.users().get(UpdatePasswordCommand.getLogin().getIduser()).update(userrepresentation);

    UserAggregate userAggregate = new UserAggregate(UpdatePasswordCommand);

    eventSourcingHandler.save(userAggregate);

  }

  /**
   * handles the busness logic for creating a user through admin
   * @param CreateUserByAdmin the command for creating a user
   * this method calls another one addUserToKeycloak to avoid redundancy to permit to add a new user to keycloak
   * response is the return of addUserToKeycloak that gives the id of the user created in keycloak
   * realmResource contains the connexion to keycloak server
   * realmResource.users() allows to manage users in keycloak by calling the specific user to reform
   * a condition is implemented to check the existence of a user
   * if the user is created successfully , the id is taken through users.get(0).getId()
   * the recuperation of the id triggers an email sent to the created user to set the password through executeActionsEmail(List.of(UPDATE_PASSWORD))
   * userid is the return of addUserToKeycloak that gives the id of the user created in keycloak
   * getIdFromLocationHeader a method that separate the url of the api called by keycloak to extract the id
   * response.getHeaderString("Location") contains the url called by keycloak server
   * userid is setted in the field id of CreateUserByAdmin
   * userAggregate permit to call the domain that handels the trigger of the event
   * eventSourcingHandler saves the object createUserCommand in the event store
   */
  @Override
  public void handle(CreateUserByAdmin CreateUserByAdmin) {
    Response response = addUserToKeycloak(CreateUserByAdmin.getUser());
    send_email(CreateUserByAdmin.getUser().getUserName());
    String userid = keycloakGeneric.getIdFromLocationHeader(response.getHeaderString("Location"));
    CreateUserByAdmin.getUser().setId(userid);
    UserAggregate userAggregate = new UserAggregate(CreateUserByAdmin);
    eventSourcingHandler.save(userAggregate);


  }

  /**
   * handles the busness logic for creating a user in keycloak
   * @param user the object to send  for creating a user
   * @return a response containing the url of the api called to create a user
   * realmResource contains the connexion to keycloak server
   * realmResource.users() allows to manage users in keycloak by calling the specific user to reform
   * search(user.getUserName()) performs a search in keycloak by username
   * a condition through switch is implemented to check the existence of the user to be added
   * if the user exists , an exception is thrown
   * if the user does not exist , the creation logic is performed
   * user can be added for the first time without or with a password through the condition user.getPassword().isEmpty()
   * kcUser is the representaion of a user in keycloak server
   * createPasswordCredentials is a call to a method that generates a password credential representation based on a password obtained from UpdatePasswordCommand
   * attributes allows to add other attributes to user in keycloak that are not present by default
   * setters are applied to userrepresentation according to fields that are requested to be updated
   * realmResource contains the connexion to keycloak server
   * usersResource allows to manage users in keycloak by calling the specific user to reform
   * user is created in keycloak through create(kcUser)
   */
  @Override
  public Response addUserToKeycloak(User user) {
    Response response = null;

    List<UserRepresentation> users = realmResource.users().search(user.getUserName());
    switch (users.size()) {
      case 1 -> {
        throw new EntityAlreadyExists(USER_ALREADY_EXISTS);

      }
      case 0 -> {
        UsersResource usersResource = realmResource.users();

        UserRepresentation kcUser = new UserRepresentation();

        if(!user.getPassword().isEmpty()){
          CredentialRepresentation credentialRepresentation = keycloakGeneric.createPasswordCredentials(
              user.getPassword());
          kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        }

        Map<String, List<String>> attributes = kcUser.getAttributes();
        if (attributes == null) {
          attributes = new HashMap<>();
        }
        attributes.put(PHONE_NUMBER, Arrays.asList(user.getPhone()));
        kcUser.setAttributes(attributes);
        kcUser.setUsername(user.getUserName());
        kcUser.setFirstName(user.getFirstName());
        kcUser.setLastName(user.getLastName());
        kcUser.setEmail(user.getEmail());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(true);
        response = usersResource.create(kcUser);

      }

    }

    return response;
  }

  /**
   * handles the busness logic for generating a token attached to a user
   * @param CreateTokenRequestCommand the object to send  for generating a token
   * keycloak a variable that holds an instance of the Keycloak class, which is used to interact with the Keycloak server's admin APIs
   * keycloakConfig a configuration class containing the connexion with keycloak server through environment variables
   * CreateTokenRequestCommand contains username , password and the corresponding token from a user who will log in
   * keycloakInstanceWithUsernameAndPaswword returns a new keycloak instance with the username and password of a specific user
   * keycloak.tokenManager().getAccessToken().getToken() gives the specific token through the new keycloak instance
   * userAggregate permit to call the domain that handels the trigger of the event
   * eventSourcingHandler saves the object createUserCommand in the event store
   */
  @Override
  public void handle(CreateTokenRequestCommand CreateTokenRequestCommand) {
    Keycloak keycloak = null;
    keycloak = this.keycloakConfig.keycloakInstanceWithUsernameAndPaswword(CreateTokenRequestCommand.getTokenRequest());
    CreateTokenRequestCommand.getTokenRequest().setToken(keycloak.tokenManager().getAccessToken().getToken());
    UserAggregate userAggregate = new UserAggregate(CreateTokenRequestCommand);

    eventSourcingHandler.save(userAggregate);

  }
  /**
   * handles the busness logic for logging out a user
   * @param CreateLogoutRequestCommand the object to send  for  logging out a user
   * realmResource contains the connexion to keycloak server
   * realmResource.users() allows to manage users in keycloak by calling the specific user to reform
   * CreateLogoutRequestCommand.getIduser() gives us the id of the specific user to log out
   * logout() a method that allows to log out from keycloak server
   * userAggregate permit to call the domain that handels the trigger of the event
   * eventSourcingHandler saves the object createUserCommand in the event store
   */
  @Override
  public void handle(CreateLogoutRequestCommand CreateLogoutRequestCommand) {
    realmResource.users().get(CreateLogoutRequestCommand.getIduser()).logout();
    UserAggregate userAggregate = new UserAggregate(CreateLogoutRequestCommand);
    eventSourcingHandler.save(userAggregate);
  }
  /**
   * temporary method to test token generation
   * @param password
   */
  @Override
  public ResponseEntity<?> changePassword(String password) {
    String userId = keycloakGeneric.getCurrentUser();
    realmResource.users().get(userId)
        .resetPassword(keycloakGeneric.createPasswordCredentials(password));
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  /**
   * handles the busness logic for sending an email
   * @param username
   * realmResource contains the connexion to keycloak server
   * realmResource.users() allows to manage users in keycloak by calling the specific user to reform
   * user gives us the specific user to reset the password
   * search(user.getUserName()) allows us to search by username
   * if user is present, keycloak server sends mail to specific user
   */
  @Override
  public void send_email(String username) {
    List<UserRepresentation> users = realmResource.users().search(username);
    if (!users.isEmpty()) {
      UserResource userResource = realmResource.users().get(users.get(0).getId());
      userResource.executeActionsEmail(List.of(UPDATE_PASSWORD));
    }
  }

  /**
   * @param ForgetPasswordCommand the object to send  email for reset password
   * send_email handles the busness logic for sending an email
   * userAggregate permit to call the domain that handels the trigger of the event
   * eventSourcingHandler saves the object createUserCommand in the event store
   */
  @Override
  public void handle(ForgetPasswordCommand ForgetPasswordCommand) {
    send_email(ForgetPasswordCommand.getUsername());
    UserAggregate userAggregate = new UserAggregate(ForgetPasswordCommand);
    eventSourcingHandler.save(userAggregate);
  }

  /**
   * handles the affectation of roles to a user
   * @param AffectRolesCommand the object to affect roles to a user
   * realmResource contains the connexion to keycloak server
   * realmResource.users() allows to manage users in keycloak by calling the specific user to reform
   * roleMappingResource gives the roles related to the specific user AffectRolesCommand.getUserId()
   * getting existing roles,  retrieves a list of existing roles from keycloak server using the listEffective()
   * RoleScopeResource is associated with a roleMappingResource for the realm level
   * deleting unwanted roles , iterates through the existing roles and checks whether each role's ID is not present in the list of role IDs obtained from the AffectRolesCommand.getRoleIds()
   * roleScopeResource.remove() removes roles from keycloak
   * adding roles,  iterates through the list of  role IDs obtained from AffectRolesCommand.getRoleIds()
   * The retrieved roles are then added to the rolesToAdd list
   * roleScopeResource.add(rolesToAdd) is used to add roles to keycloak
   * method is handled by Exception
   * if a user or a role does not exist, we raise EntityNotFoundException with a specific message
   * userAggregate permit to call the domain that handels the trigger of the event
   * eventSourcingHandler saves the object AffectRolesCommand in the event store
   */
  @Override
  public void handle(AffectRolesCommand AffectRolesCommand) {
    UserResource userResource = realmResource.users().get(AffectRolesCommand.getUserId());
    RoleMappingResource roleMappingResource = userResource.roles();
    RoleScopeResource roleScopeResource = roleMappingResource.realmLevel();
    List<RoleRepresentation> existingRoles = roleScopeResource.listEffective();
    for (RoleRepresentation existingRole : existingRoles) {
      if (!AffectRolesCommand.getRoleIds().contains(existingRole.getId())) {
        roleScopeResource.remove(Collections.singletonList(existingRole));
      }
    }
    List<RoleRepresentation> rolesToAdd = new ArrayList<>();
    for (String roleId : AffectRolesCommand.getRoleIds()) {
      RoleRepresentation roleRepresentation = realmResource.roles().list().stream()
          .filter(a -> a.getId().equals(roleId)).findFirst().get();
      rolesToAdd.add(roleRepresentation);
    }
    roleScopeResource.add(rolesToAdd);
    UserAggregate userAggregate = new UserAggregate(AffectRolesCommand);
    eventSourcingHandler.save(userAggregate);
  }
}




