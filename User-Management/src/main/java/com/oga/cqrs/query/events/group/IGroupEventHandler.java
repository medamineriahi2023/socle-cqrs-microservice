package com.oga.cqrs.query.events.group;


import com.oga.cqrs.command.events.group.*;


public interface IGroupEventHandler {
  /**
   * Handles the given create a group event.
   *
   * @param event the create group event to handle
   */

  void on(GroupCreated event);

  /**
   * Handles the given update a group event.
   *
   * @param event the update group event to handle
   */

  void on(GroupUpdated event);
  /**
   * Handles the given update a delete event.
   *
   * @param event the delete group event to handle
   */

  void on(GroupDeleted event);
}
