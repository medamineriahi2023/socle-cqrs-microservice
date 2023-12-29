package com.oga.cqrs.query.events.organization;



import com.oga.cqrs.command.events.organizations.OrganizationCreated;


/**
 * This interface represents an event handler for handling events.
 */

public interface IOrganizationEventHandler {

    /**
     * Handles the given create a user event.
     *
     * @param event the create user event to handle
     */

    void on(OrganizationCreated event);
}
