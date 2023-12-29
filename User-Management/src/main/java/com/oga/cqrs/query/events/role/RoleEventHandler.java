package com.oga.cqrs.query.events.role;

import com.oga.cqrs.command.events.role.*;
import com.oga.cqrs.query.rest.dto.Role;
import com.oga.cqrs.query.rest.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.oga.cqrs.command.rest.constant.Constant.*;

@Service
public class RoleEventHandler implements IRoleEventHandler{
  @Autowired
  private RoleRepository roleRepository;
  /**
   * handles the busness logic for creating a role , called when  an event RoleCreated occurs.
   * @param event the create role event to handle
   * role, a variable  that will be constructed from the event
   * Inside the try block:
   * The properties from the event.getRole() is used to build a role object using the builder pattern
   * Inside the catch block:
   * If there's a NumberFormatException , it throws an IllegalArgumentException with a message (NON_VALID_ID)
   * roleRepository.save(role), persists the role in a database
   */
  @Override
  public void on(RoleCreated event) {
    Role role;

    try{
      role = Role.builder()
          .id(event.getRole().getId())
          .name(event.getRole().getName())
          .description(event.getRole().getDescription())
          .build();
    }catch (NumberFormatException e) {
      throw new IllegalArgumentException(NON_VALID_ID,e);
    }

    roleRepository.save(role);
  }
  /**
   * handles the busness logic for updating a role , called when  an event RoleUpdated occurs.
   * @param event the update role event to handle
   * role, a variable  that will be constructed from the event
   * Inside the try block:
   * The properties from the event.getRole() is used to build a role object using the builder pattern
   * Inside the catch block:
   * If there's a NumberFormatException , it throws an IllegalArgumentException with a message (NON_VALID_ID)
   * roleRepository.save(role), persists the role in a database
   * for nosql(mongodb) create and update requires save()
   */

  @Override
  public void on(RoleUpdated event) {
    Role role;

    try{
      role = Role.builder()
          .id(event.getRole().getId())
          .name(event.getRole().getName())
          .description(event.getRole().getDescription())
          .build();
    }catch (NumberFormatException e) {
      throw new IllegalArgumentException(NON_VALID_ID,e);
    }

    roleRepository.save(role);
  }
  /**
   * @param event the delete role event to handle
   * roleRepository.deleteById(event.getIdrole()), removes the role from a database
   */
  @Override
  public void on(RoleDeleted event) {
    roleRepository.deleteById(event.getIdrole());
  }
}
