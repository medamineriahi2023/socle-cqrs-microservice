package com.oga.cqrs.command.domain;


import com.oga.cqrs.command.commands.user.*;
import com.oga.cqrs.command.events.user.*;
import com.oga.cqrs.command.rest.dto.User;
import com.oga.cqrsref.domain.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAggregate extends AggregateRoot {
  private String  id;
  private User user;




  /**
   * Constructor for the user aggregate, taking a CreateUserCommand as input.
   * This constructor raises a CreateUserEvent with the data from the command.
   * @param command The CreateUserCommand
   */
  public UserAggregate(CreateUserCommand command) {
    raiseEvent(UserCreated.builder()
        .identifier(command.getId())
        .user(command.getUser())
        .build());
  }

  /**
   * Constructor for the user aggregate, taking a UpdateUserCommand as input.
   * This constructor raises a UpdateUserEvent with the data from the command.
   * @param command The UpdateUserCommand
   */
  public UserAggregate(UpdateUserCommand command) {
    raiseEvent(UserUpdated.builder()
        .identifier(command.getId())
        .user(command.getUser())
        .build());
  }
  /**
   * Constructor for the user aggregate, taking a DeleteUserCommand as input.
   * This constructor raises a UpdateUserEvent with the data from the command.
   * @param command The DeleteUserCommand
   */
  public UserAggregate(DeleteUserCommand command) {
    raiseEvent(UserDeleted.builder()
        .identifier(command.getId())
        .iduser(command.getIduser())
        .build());
  }
  /**
   * Constructor for the user aggregate, taking a UserCreatedByAdmin as input.
   * This constructor raises a UserCreatedByAdmin with the data from the command.
   * @param command The UserCreatedByAdmin
   */
  public UserAggregate(CreateUserByAdmin command) {
    raiseEvent(UserCreatedByAdmin.builder()
        .identifier(command.getId())
        .user(command.getUser())
        .build());
  }
  /**
   * Constructor for the user aggregate, taking a UserCreatedByAdmin as input.
   * This constructor raises a UserCreatedByAdmin with the data from the command.
   * @param command The UserCreatedByAdmin
   */
  public UserAggregate(CreateTokenRequestCommand command) {
    raiseEvent(TokenRequested.builder()
        .identifier(command.getId())
        .TokenRequest(command.getTokenRequest())
        .build());
  }
  /**
   * Constructor for the user aggregate, taking a CreateLogoutRequestCommand as input.
   * This constructor raises a LogoutRequested with the data from the command.
   * @param command The CreateLogoutRequestCommand
   */
  public UserAggregate(CreateLogoutRequestCommand command) {
    raiseEvent(LogoutRequested.builder()
        .identifier(command.getId())
        .iduser(command.getIduser())
        .build());
  }
  /**
   * Constructor for the user aggregate, taking a ForgetPasswordCommand as input.
   * This constructor raises a passwordforgotten with the data from the command.
   * @param command The ForgetPasswordCommand
   */
  public UserAggregate(ForgetPasswordCommand command) {
    raiseEvent(passwordforgotten.builder()
        .identifier(command.getId())
        .username(command.getUsername())
        .build());
  }
  /**
   * Constructor for the user aggregate, taking a AffectRolesCommand as input.
   * This constructor raises a RolesAffected with the data from the command.
   * @param command The AffectRolesCommand
   */
  public UserAggregate(AffectRolesCommand command) {
    raiseEvent(RolesAffected.builder()
        .identifier(command.getId())
        .userId(command.getUserId())
        .roleIds(command.getRoleIds())
        .build());
  }
  /**
   * Applies a RolesAffected to the user aggregate, updating its state.
   * This method sets the RolesAffected of the user to the one in the event.
   * @param event The RolesAffected
   */
  public void apply (RolesAffected event)
  {
    this.id=event.getIdentifier();

  }
  /**
   * Applies a passwordforgotten to the user aggregate, updating its state.
   * This method sets the passwordforgotten of the user to the one in the event.
   * @param event The passwordforgotten
   */
  public void apply (passwordforgotten event)
  {
    this.id=event.getIdentifier();

  }
  /**
   * Applies a LogoutRequested to the user aggregate, updating its state.
   * This method sets the id of the user to the one in the event.
   * @param event The LogoutRequested
   */
  public void apply (LogoutRequested event)
  {
    this.id=event.getIdentifier();

  }
  /**
   * Applies a UserCreatedByAdmin to the user aggregate, updating its state.
   * This method sets the id of the user to the one in the event.
   * @param event The UserCreatedByAdmin
   */
  public void apply (TokenRequested event)
  {
    this.id=event.getIdentifier();

  }
  /**
   * Applies a UserCreatedByAdmin to the user aggregate, updating its state.
   * This method sets the id of the user to the one in the event.
   * @param event The UserCreatedByAdmin
   */
  public void apply (UserCreatedByAdmin event)
  {
    this.id=event.getIdentifier();

  }

  public UserAggregate(UpdatePasswordCommand command) {
    raiseEvent(PasswordUpdated.builder()
        .identifier(command.getId())
        .login(command.getLogin())
        .build());
  }

  /**
   * Applies a CreateUserEvent to the user aggregate, updating its state.
   * This method sets the id of the user to the one in the event.
   * @param event The CreateUserEvent
   */
  public void apply (UserCreated event)
  {
    this.id=event.getIdentifier();

  }
  /**
   * Applies a UpdateUserEvent to the user aggregate, updating its state.
   * This method sets the id of the user to the one in the event.
   * @param event The UpdateUserEvent
   */
  public void apply (UserUpdated event)
  {
    this.id=event.getIdentifier();

  }
  /**
   * Applies a DeleteUserEvent to the user aggregate, updating its state.
   * This method sets the id of the user to the one in the event.
   * @param event The DeleteUserEvent
   */
  public void apply (UserDeleted event)
  {
    this.id=event.getIdentifier();

  }
  /**
   * Applies a UpdatePasswordEvent to the user aggregate, updating its state.
   * This method sets the id of the aggregate to the one in the event.
   * @param event The DeleteUserEvent
   */
  public void apply (PasswordUpdated event)
  {
    this.id=event.getIdentifier();

  }
}
