package com.oga.cqrs.command.rest.controller;


import com.oga.cqrs.command.commands.user.*;
import com.oga.cqrs.command.handlers.user.UserCommandHandlerInterface;
import com.oga.cqrs.command.rest.dto.*;
import com.oga.cqrs.command.rest.response.BasicResponse;
import com.oga.cqrsref.infrastructure.CommandDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.oga.cqrs.command.rest.constant.Constant.*;
@ComponentScan(basePackages="com.oga.cqrsref.controller")
@RestController
@RequestMapping(path = "/security/v1")
@Slf4j
public class UserController {


  private final CommandDispatcher commandDispatcher;
  private final UserCommandHandlerInterface iUserCommandHandlerInterface;
  @Autowired
  public UserController(CommandDispatcher commandDispatcher,UserCommandHandlerInterface iUserCommandHandlerInterface) {
    this.commandDispatcher = commandDispatcher;
    this.iUserCommandHandlerInterface=iUserCommandHandlerInterface;
  }

  /**
   Handles a request to create a new User.
   @param user A User object representing the user to create.
   @return A ResponseEntity containing a BaseResponse object with a message and the ID of the new User.
   new CreateUserCommand()  creates a new instance of CreateUserCommand
   UUID.randomUUID().toString() generates a random UUID and converts it to a string used to keep the events with an unique id
   command.setId(id) ,  ID is set into the CreateUserCommand
   command.setUser(user) ,  user is set into the CreateUserCommand
   commandDispatcher.send(command) dispatches the CreateUserCommand to the commandDispatcher, which will handle the command and execute the logic associated with creating a user
   If the request is successful, a status of {@link HttpStatus#CREATED} is returned.
   If the request is unsuccessful due to a bad request, a status of {@link HttpStatus#BAD_REQUEST} is returned.
   If an error occurs during processing, a status of {@link HttpStatus#INTERNAL_SERVER_ERROR} is returned.
   */

  @PostMapping("users")
  public ResponseEntity<BasicResponse> createUser(@Valid @RequestBody User user) {
    CreateUserCommand command=new CreateUserCommand();
    var id = UUID.randomUUID().toString();
    command.setId(id);
    command.setUser(user);
    commandDispatcher.send(command);

    return new ResponseEntity<>(new BasicResponse(SUCCESS_MESSAGE_CREATION, command.getId()), HttpStatus.CREATED);


  }

  /**
   Handles a request to update a new User.
   @param user A User object representing the user to update.
   @return A ResponseEntity containing a BaseResponse object with a message and the ID of the new User.
   new UpdateUserCommand()  creates a new instance of CreateUserCommand
   UUID.randomUUID().toString() generates a random UUID and converts it to a string used to keep the events with an unique id
   command.setId(id) ,  ID is set into the UpdateUserCommand
   command.setUser(user) ,  user is set into the UpdateUserCommand
   commandDispatcher.send(command) dispatches the CreateUserCommand to the commandDispatcher, which will handle the command and execute the logic associated with updating a user
   If the request is successful, a status of {@link HttpStatus#CREATED} is returned.
   If the request is unsuccessful due to a bad request, a status of {@link HttpStatus#BAD_REQUEST} is returned.
   If an error occurs during processing, a status of {@link HttpStatus#INTERNAL_SERVER_ERROR} is returned.
   */
  @PutMapping("users")
  public ResponseEntity<BasicResponse> updateUser(@Valid @RequestBody User user) {
    UpdateUserCommand command=new UpdateUserCommand();
    var id = UUID.randomUUID().toString();
    command.setId(id);
    command.setUser(user);
    commandDispatcher.send(command);
    return new ResponseEntity<>(new BasicResponse(SUCCESS_MESSAGE_UPDATE, command.getId()), HttpStatus.CREATED);
  }



  /**
   Handles a request to delete a new User.
   @param iduser An id user representing the user to delete.
   @return A ResponseEntity containing a BaseResponse object with a message and the ID of the new User.
   new DeleteUserCommand()  creates a new instance of DeleteUserCommand
   UUID.randomUUID().toString() generates a random UUID and converts it to a string used to keep the events with a unique id
   command.setId(id) ,  ID is set into the DeleteUserCommand
   command.setIduser(iduser) ,  user's id is set into the DeleteUserCommand
   commandDispatcher.send(command) dispatches the DeleteUserCommand to the commandDispatcher, which will handle the command and execute the logic associated with deleting a user
   If the request is successful, a status of {@link HttpStatus#CREATED} is returned.
   If the request is unsuccessful due to a bad request, a status of {@link HttpStatus#BAD_REQUEST} is returned.
   If an error occurs during processing, a status of {@link HttpStatus#INTERNAL_SERVER_ERROR} is returned.
   */


  @DeleteMapping("users/{iduser}")
  public ResponseEntity<BasicResponse> deleteUser(@PathVariable(value = "iduser") String iduser ) {
    DeleteUserCommand command=new DeleteUserCommand();
    var id = UUID.randomUUID().toString();
    command.setId(id);
    command.setIduser(iduser);
    commandDispatcher.send(command);
    return new ResponseEntity<>(new BasicResponse(SUCCESS_MESSAGE_DELETE, command.getId()), HttpStatus.CREATED);

  }



  /**
   Handles a request to create a new User by admin .
   @param user An  user representing the user to create.
   @return A ResponseEntity containing a BaseResponse object with a message and the ID of the new User.
   new CreateUserByAdmin()  creates a new instance of CreateUserByAdmin
   UUID.randomUUID().toString() generates a random UUID and converts it to a string used to keep the events with a unique id
   command.setId(id) ,  ID is set into the CreateUserByAdmin
   command.setUser(user) ,  user is set into the CreateUserByAdmin
   commandDispatcher.send(command) dispatches the CreateUserCommand to the commandDispatcher, which will handle the command and execute the logic associated with creating a user
   If the request is successful, a status of {@link HttpStatus#CREATED} is returned.
   If the request is unsuccessful due to a bad request, a status of {@link HttpStatus#BAD_REQUEST} is returned.
   If an error occurs during processing, a status of {@link HttpStatus#INTERNAL_SERVER_ERROR} is returned.
   */
  @PostMapping("users/createByAdmin")
  public ResponseEntity<BasicResponse> createByAdmin(@Valid @RequestBody User user ) {
    CreateUserByAdmin command=new CreateUserByAdmin();
    var id = UUID.randomUUID().toString();
    command.setId(id);
    command.setUser(user);
    commandDispatcher.send(command);
    return new ResponseEntity<>(new BasicResponse(SUCCESS_MESSAGE_CREATION, command.getId()), HttpStatus.CREATED);
  }
  /**
   Handles a request to updating a  User password.
   @param login  representing the id and the password  to update.
   @return A ResponseEntity containing a BaseResponse object with a message and the ID of the new User.
   new UpdatePasswordCommand()  creates a new instance of UpdatePasswordCommand
   UUID.randomUUID().toString() generates a random UUID and converts it to a string used to keep the events with a unique id
   command.setId(id) ,  ID is set into the UpdatePasswordCommand
   command.setUser(user) ,  user is set into the UpdatePasswordCommand
   commandDispatcher.send(command) dispatches the CreateUserCommand to the commandDispatcher, which will handle the command and execute the logic associated with updating a user's password
   If the request is successful, a status of {@link HttpStatus#CREATED} is returned.
   If the request is unsuccessful due to a bad request, a status of {@link HttpStatus#BAD_REQUEST} is returned.
   If an error occurs during processing, a status of {@link HttpStatus#INTERNAL_SERVER_ERROR} is returned.
   */

  @PostMapping("users/changepassword")
  public ResponseEntity<BasicResponse> updatePassword(@RequestBody Login login ) {
    UpdatePasswordCommand command=new UpdatePasswordCommand();
    var id = UUID.randomUUID().toString();
    command.setId(id);
    command.setLogin(login);
    commandDispatcher.send(command);

    return new ResponseEntity<>(new BasicResponse(SUCCESS_MESSAGE_UPDATE_PASSWORD, command.getId()), HttpStatus.CREATED);

  }
  /**
   Handles a request to get the token.
   @param login  representing the username and the password  to enter.
   @return A ResponseEntity containing a BaseResponse object with a message and the ID of the token.
   new CreateTokenRequestCommand()  creates a new instance of CreateTokenRequestCommand
   UUID.randomUUID().toString() generates a random UUID and converts it to a string used to keep the events with an unique id
   command.setId(id) ,  ID is set into the CreateTokenRequestCommand
   command.setTokenRequest(login) ,  user is set into the CreateTokenRequestCommand
   commandDispatcher.send(command) dispatches the CreateTokenRequestCommand to the commandDispatcher, which will handle the command and execute the logic associated with generating a user's token
   If the request is successful, a status of {@link HttpStatus#CREATED} is returned.
   If the request is unsuccessful due to a bad request, a status of {@link HttpStatus#BAD_REQUEST} is returned.
   If an error occurs during processing, a status of {@link HttpStatus#INTERNAL_SERVER_ERROR} is returned.
   */

  @PostMapping("users/login")
  public ResponseEntity<BasicResponse> login(@RequestBody TokenRequestLogin login ) {
    CreateTokenRequestCommand command=new CreateTokenRequestCommand();
    var id = UUID.randomUUID().toString();
    command.setId(id);
    command.setTokenRequest(login);
    commandDispatcher.send(command);

    return new ResponseEntity<>(new BasicResponse(TOKEN_GENERATION, command.getTokenRequest().getToken()), HttpStatus.CREATED);

  }
  /**
   Handles a request to log out a user.
   @param iduser An id user representing the user to logged out.
   @return A ResponseEntity containing a BaseResponse object with a message and the ID of the new User.
   new CreateLogoutRequestCommand()  creates a new instance of CreateLogoutRequestCommand
   UUID.randomUUID().toString() generates a random UUID and converts it to a string used to keep the events with a unique id
   command.setId(id) ,  ID is set into the CreateLogoutRequestCommand
   command.setIduser(iduser) ,  user's id is set into the CreateLogoutRequestCommand
   commandDispatcher.send(command) dispatches the CreateLogoutRequestCommand to the commandDispatcher, which will handle the command and execute the logic associated with logging out a user
   If the request is successful, a status of {@link HttpStatus#CREATED} is returned.
   If the request is unsuccessful due to a bad request, a status of {@link HttpStatus#BAD_REQUEST} is returned.
   If an error occurs during processing, a status of {@link HttpStatus#INTERNAL_SERVER_ERROR} is returned.
   */

  @PostMapping("users/logout/{iduser}")
  public ResponseEntity<BasicResponse> logout(@PathVariable(value = "iduser") String iduser ) {
    CreateLogoutRequestCommand command=new CreateLogoutRequestCommand();
    var id = UUID.randomUUID().toString();
    command.setId(id);
    command.setIduser(iduser);
    commandDispatcher.send(command);

    return new ResponseEntity<>(new BasicResponse(LOGOUT, iduser), HttpStatus.CREATED);

  }

  /** temporary api
   * test the token generation
   * @param newPass
   * @return
   */
  @PostMapping(path = "users/changeCredentials/{newPass}")
  public ResponseEntity<?> changePassword(@PathVariable(value = "newPass") String newPass) {
    return iUserCommandHandlerInterface.changePassword(newPass);
  }
  /**
   Handles a request to log out a user.
   @param userName A username representing the user to send email to.
   @return A ResponseEntity containing a BaseResponse object with a message and the ID of the username.
   new ForgetPasswordCommand()  creates a new instance of ForgetPasswordCommand
   UUID.randomUUID().toString() generates a random UUID and converts it to a string used to keep the events with a unique id
   command.setId(id) ,  ID is set into the ForgetPasswordCommand
   command.setUsername(userName) ,  user's id is set into the ForgetPasswordCommand
   commandDispatcher.send(command) dispatches the ForgetPasswordCommand to the commandDispatcher, which will handle the command and execute the logic associated with sending an email to a user
   If the request is successful, a status of {@link HttpStatus#CREATED} is returned.
   If the request is unsuccessful due to a bad request, a status of {@link HttpStatus#BAD_REQUEST} is returned.
   If an error occurs during processing, a status of {@link HttpStatus#INTERNAL_SERVER_ERROR} is returned.
   */
  @PostMapping(path = "users/forgetPassword/{userName}")
  public ResponseEntity<?> ForgetPassword(@PathVariable(value = "userName") String userName) {
    ForgetPasswordCommand command=new ForgetPasswordCommand();
    var id = UUID.randomUUID().toString();
    command.setId(id);
    command.setUsername(userName);
    commandDispatcher.send(command);

    return new ResponseEntity<>(PASSWORD_FORGOTTEN + userName, HttpStatus.CREATED);
  }

  /**
   Handles a request affect roles to a user
   @param requestBody A requestBody userid and rolesIds.
   @return A ResponseEntity containing a BaseResponse object with a message and the ID of the username.
   new AffectRolesCommand()  creates a new instance of AffectRolesCommand
   UUID.randomUUID().toString() generates a random UUID and converts it to a string used to keep the events with a unique id
   command.setId(id) ,  ID is set into the AffectRolesCommand
   command.setUserId(userId) ,  user's id is set into the AffectRolesCommand
   command.setRoleIds(roleIds) ,  roles id is set into the AffectRolesCommand
   commandDispatcher.send(command) dispatches the AffectRolesCommand to the commandDispatcher, which will handle the command and execute the logic associated with the role affectation
   If the request is successful, a status of {@link HttpStatus#CREATED} is returned.
   If the request is unsuccessful due to a bad request, a status of {@link HttpStatus#BAD_REQUEST} is returned.
   If an error occurs during processing, a status of {@link HttpStatus#INTERNAL_SERVER_ERROR} is returned.
   */
  @PostMapping(path = "users/assignRolesToUser")
  public ResponseEntity<?> assignrolestouser(@RequestBody Map<String, Object> requestBody) {
    String userId = (String) requestBody.get("userId");
    List<String> roleIds = (List<String>) requestBody.get("roleIds");
    AffectRolesCommand command=new AffectRolesCommand();
    var id = UUID.randomUUID().toString();
    command.setId(id);
    command.setUserId(userId);
    command.setRoleIds(roleIds);
    commandDispatcher.send(command);
    return new ResponseEntity<>(command, HttpStatus.CREATED);
  }
}
