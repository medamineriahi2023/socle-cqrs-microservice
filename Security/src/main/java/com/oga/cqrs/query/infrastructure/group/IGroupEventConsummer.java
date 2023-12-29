package com.oga.cqrs.query.infrastructure.group;


import com.oga.cqrs.command.events.group.*;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;



/**
 * An interface representing a consumer of events. Implementations of this interface can consume
 * different types of events based on the provided payload, which should match the expected type
 * for that implementation.
 */
public interface IGroupEventConsummer {

    /**
     * Consume an event of type `GroupCreated` with the provided payload.
     *
     * @param event The event payload to be consumed.
     * @param ack The acknowledgment to be sent after the event is consumed.
     */

    void consume(@Payload GroupCreated event, Acknowledgment ack);

    /**
     * Consume an event of type `GroupUpdated` with the provided payload.
     *
     * @param event The event payload to be consumed.
     * @param ack The acknowledgment to be sent after the event is consumed.
     */

    void consume(@Payload GroupUpdated event, Acknowledgment ack);
    /**
     * Consume an event of type `GroupDeleted` with the provided payload.
     *
     * @param event The event payload to be consumed.
     * @param ack The acknowledgment to be sent after the event is consumed.
     */

    void consume(@Payload GroupDeleted event, Acknowledgment ack);
}
