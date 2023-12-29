package com.oga.cqrs.query.events.role;


import com.oga.cqrs.command.events.role.*;

public interface IRoleEventHandler {
  /**
   * Handles the given create a role event.
   *
   * @param event the create role event to handle
   */

  void on(RoleCreated event);

  /**
   * Handles the given update a role event.
   *
   * @param event the update role event to handle
   */

  void on(RoleUpdated event);
  /**
   * Handles the given delete a role event.
   *
   * @param event the delete role event to handle
   */

  void on(RoleDeleted event);
}
