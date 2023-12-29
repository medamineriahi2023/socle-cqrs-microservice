package com.oga.cqrs.command.handlers.user;


import com.oga.cqrs.command.commands.user.*;
import com.oga.cqrs.command.rest.dto.User;
import org.springframework.http.ResponseEntity;

import javax.ws.rs.core.Response;


/**
 * Interface for handling different types of commands.
 */
public interface UserCommandHandlerInterface {

    /**
     Handles the creation of a user with the given command.
     @param createUserCommand the command for creating a user
     */

    void handle(CreateUserCommand createUserCommand) throws Exception;

    /**
     Handles the update of a user with the given command.
     @param updateUserCommand the command for updating a user
     */
    void handle(UpdateUserCommand updateUserCommand);

    /**
     Handles the update of a user's password with the given command.
     @param UpdatePasswordCommand the command for updating a user's password
     */
    void handle(UpdatePasswordCommand UpdatePasswordCommand);
    /**
     Handles the update of a user with the given command.
     @param deleteUserCommand the command for deleting a user
     */
    void handle(DeleteUserCommand deleteUserCommand);

    /**
     Handles the creation of a user with the given command.
     @param CreateUserByAdmin the command for creating a user
     */
    void handle(CreateUserByAdmin CreateUserByAdmin);
    /**
     Handles the creation of a user with sending an email in keycloak server.
     @param user the object to send  for creating a user
     */
    Response addUserToKeycloak(User user);
    /**
     Handles generation of a token .
     @param CreateTokenRequestCommand the object to send  for generating a token
     */
    void handle(CreateTokenRequestCommand CreateTokenRequestCommand) ;
    /**
     Handles the log out of a user .
     @param CreateLogoutRequestCommand the object to send  for log out a user
     */
    void handle(CreateLogoutRequestCommand CreateLogoutRequestCommand) ;

    /**
     * temporary method to test token generation
     * @param password
     */
    ResponseEntity<?> changePassword(String password) ;

    /**
     * handles the logic of send mail
     * @param username
     */
    void send_email(String username) ;
    /**
     Handles forget password of a user .
     @param ForgetPasswordCommand the object to send  email for reset password
     */
    void handle(ForgetPasswordCommand ForgetPasswordCommand) ;
    /**
     Handles the affectation of roles .
     @param AffectRolesCommand the object to affect roles to a user
     */
    void handle(AffectRolesCommand AffectRolesCommand) ;
}
