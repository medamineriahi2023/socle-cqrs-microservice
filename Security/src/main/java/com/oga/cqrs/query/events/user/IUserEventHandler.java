package com.oga.cqrs.query.events.user;



import com.oga.cqrs.command.events.user.*;






/**
 * This interface represents an event handler for handling events.
 */

public interface IUserEventHandler {

    /**
     * Handles the given create a user event.
     *
     * @param event the create user event to handle
     */

    void on(UserCreated event);

    /**
     * Handles the given update a user event.
     *
     * @param event the update user event to handle
     */

    void on(UserUpdated event);
    /**
     * Handles the given delete a user event.
     *
     * @param event the delete user event to handle
     */

    void on(UserDeleted event);
    /**
     * Handles the given create a user event.
     *
     * @param event the UserCreatedByAdmin event to handle
     */
    void on(UserCreatedByAdmin event);


    /**
     * Handles the given update a user event.
     *
     * @param event the UpdatePasswordEvent event to handle
     */
    void on(PasswordUpdated event);
    /**
     * Handles the given user to affect roles.
     *
     * @param event the RolesAffected event to handle
     */
    void on(RolesAffected event);
}
