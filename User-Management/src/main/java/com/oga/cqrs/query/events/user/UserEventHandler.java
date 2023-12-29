package com.oga.cqrs.query.events.user;

import com.oga.cqrs.command.events.user.*;
import com.oga.cqrs.query.rest.dto.User;
import com.oga.cqrs.query.rest.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import static com.oga.cqrs.command.rest.constant.Constant.NON_VALID_ID;
import static com.oga.cqrs.command.rest.constant.Constant.USER_NOT_FOUND;

/**
 * An event handler for handling user events by saving the user to a MongoDB repository.
 */


@Service
@Slf4j
public class UserEventHandler implements IUserEventHandler {

  @Autowired
  private UserRepository userRepository;

  /**
   * handles the busness logic for creating a user , called when  an event UserCreated occurs.
   * @param event the create user event to handle
   * user, a variable  that will be constructed from the event
   * Inside the try block:
   * The properties from the event.getUser() is used to build a User object using the builder pattern
   * Inside the catch block:
   * If there's a NumberFormatException , it throws an IllegalArgumentException with a message (NON_VALID_ID)
   * userRepository.save(user), persists the user in a database
   */
  @Override
  public void on(UserCreated event) {
    User user;

    try{
      user = User.builder()
          .id(event.getUser().getId())
          .firstName(event.getUser().getFirstName())
          .lastName(event.getUser().getLastName())
          .email(event.getUser().getEmail())
          .userName(event.getUser().getUserName())
          .password(event.getUser().getPassword())
          .phone(event.getUser().getPhone())
          .build();
    }catch (NumberFormatException e) {
      throw new IllegalArgumentException(NON_VALID_ID,e);
    }

    userRepository.save(user);
  }
  /**
   * handles the busness logic for updating a user , called when  an event UserUpdated occurs.
   * @param event the update user event to handle
   * user, a variable  that will be constructed from the event
   * Inside the try block:
   * The properties from the event.getUser() is used to build a User object using the builder pattern
   * Inside the catch block:
   * If there's a NumberFormatException , it throws an IllegalArgumentException with a message (NON_VALID_ID)
   * userRepository.save(user), persists the user in a database
   * for nosql(mongodb) create and update requires save()
   */

  @Override
  public void on(UserUpdated event) {
    User user ;

    try{
      user = User.builder()
          .id(event.getUser().getId())
          .firstName(event.getUser().getFirstName())
          .lastName(event.getUser().getLastName())
          .email(event.getUser().getEmail())
          .userName(event.getUser().getUserName())
          .password(event.getUser().getPassword())
          .phone(event.getUser().getPhone())
          .build();
    }catch (NumberFormatException e) {
      throw new IllegalArgumentException(NON_VALID_ID,e);
    }

    userRepository.save(user);
  }

  /**
   * @param event the delete user event to handle
   * userRepository.deleteById(event.getIduser()), removes the user from a database
   */
  @Override
  public void on(UserDeleted event) {
    userRepository.deleteById(event.getIduser());
  }
  /**
   * handles the busness logic for creating a user by admin, called when  an event UserCreatedByAdmin occurs.
   * @param event the creation of a user event to handle
   * user, a variable  that will be constructed from the event
   * Inside the try block:
   * The properties from the event.getUser() is used to build a User object using the builder pattern
   * Inside the catch block:
   * If there's a NumberFormatException , it throws an IllegalArgumentException with a message (NON_VALID_ID)
   * userRepository.save(user), persists the user in a database
   * for nosql(mongodb) create and update requires save()
   */

  @Override
  public void on(UserCreatedByAdmin event) {
    User user ;

    try{
      user = User.builder()
          .id(event.getUser().getId())
          .firstName(event.getUser().getFirstName())
          .lastName(event.getUser().getLastName())
          .email(event.getUser().getEmail())
          .userName(event.getUser().getUserName())
          .password(event.getUser().getPassword())
          .phone(event.getUser().getPhone())
          .build();
    }catch (NumberFormatException e) {
      throw new IllegalArgumentException(NON_VALID_ID,e);
    }

    userRepository.save(user);
  }

  /**
   * @param event the UpdatePasswordEvent event to handle
   * userRepository.findById, check the existance of a user
   * ifPresent brings a condition
   * if user is found , update password
   * if user is not found , throw an exception EntityNotFoundException(USER_NOT_FOUND)
   */
  @Override
  public void on(PasswordUpdated event) {

    try{
      userRepository.findById(event.getLogin().getIduser()).ifPresent(user->
      {user.setPassword(event.getLogin().getPassword());
        userRepository.save(user);
      });

    }catch (NotFoundException e) {
      throw new EntityNotFoundException(USER_NOT_FOUND);
    }

  }
  /**
   * @param event the RolesAffected event to handle
   * userRepository.findById, check the existance of a user
   * ifPresent brings a condition
   * user.setRolesids(event.getRoleIds()) sets roles to the specific user
   * if user is found , update user
   * if user is not found , throw an exception EntityNotFoundException(USER_NOT_FOUND)
   */
  @Override
  public void on(RolesAffected event) {
    try{
      User user = userRepository.findById(event.getUserId()).get();
      user.setRolesids(event.getRoleIds());
      userRepository.save(user);
    }catch (NotFoundException e) {
      throw new EntityNotFoundException(USER_NOT_FOUND);
    }

  }
}
