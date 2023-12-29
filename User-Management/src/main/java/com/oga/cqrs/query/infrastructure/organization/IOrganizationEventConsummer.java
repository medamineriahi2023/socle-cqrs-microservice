package com.oga.cqrs.query.infrastructure.organization;


import com.oga.cqrs.command.events.group.GroupCreated;
import com.oga.cqrs.command.events.group.GroupDeleted;
import com.oga.cqrs.command.events.group.GroupUpdated;
import com.oga.cqrs.command.events.organizations.OrganizationCreated;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;



/**
 * An interface representing a consumer of events. Implementations of this interface can consume
 * different types of events based on the provided payload, which should match the expected type
 * for that implementation.
 */
public interface IOrganizationEventConsummer {

    /**
     * Consume an event of type `GroupCreated` with the provided payload.
     *
     * @param event The event payload to be consumed.
     * @param ack The acknowledgment to be sent after the event is consumed.
     */

    void consume(@Payload OrganizationCreated event, Acknowledgment ack);

}
