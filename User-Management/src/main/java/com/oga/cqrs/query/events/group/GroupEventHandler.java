package com.oga.cqrs.query.events.group;

import com.oga.cqrs.command.events.group.*;
import com.oga.cqrs.query.rest.dto.Group;
import com.oga.cqrs.query.rest.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.oga.cqrs.command.rest.constant.Constant.*;

@Service
public class GroupEventHandler implements IGroupEventHandler {
  @Autowired
  private GroupRepository groupRepository;
  /**
   * handles the busness logic for creating a group , called when  an event GroupCreated occurs.
   * @param event the create group event to handle
   * group, a variable  that will be constructed from the event
   * Inside the try block:
   * The properties from the event.getGroup() is used to build a group object using the builder pattern
   * Inside the catch block:
   * If there's a NumberFormatException , it throws an IllegalArgumentException with a message (NON_VALID_ID)
   * groupRepository.save(group), persists the group in a database
   */
  @Override
  public void on(GroupCreated event) {
    Group group;

    try{
      group = Group.builder()
          .id(event.getGroup().getId())
          .name(event.getGroup().getName())
          .build();
    }catch (NumberFormatException e) {
      throw new IllegalArgumentException(NON_VALID_ID,e);
    }

    groupRepository.save(group);
  }
  /**
   * handles the busness logic for updating a group , called when  an event GroupUpdated occurs.
   * @param event the update group event to handle
   * group, a variable  that will be constructed from the event
   * Inside the try block:
   * The properties from the event.getGroup() is used to build a group object using the builder pattern
   * Inside the catch block:
   * If there's a NumberFormatException , it throws an IllegalArgumentException with a message (NON_VALID_ID)
   * groupRepository.save(group), persists the group in a database
   * for nosql(mongodb) create and update requires save()
   */

  @Override
  public void on(GroupUpdated event) {
    Group group;

    try{
      group = Group.builder()
          .id(event.getGroup().getId())
          .name(event.getGroup().getName())
          .build();
    }catch (NumberFormatException e) {
      throw new IllegalArgumentException(NON_VALID_ID,e);
    }

    groupRepository.save(group);
  }
  /**
   * @param event the delete group event to handle
   * groupRepository.deleteById(event.getIdgroup()), removes the group from a database
   */
  @Override
  public void on(GroupDeleted event) {
    groupRepository.deleteById(event.getIdgroup());
  }
}
