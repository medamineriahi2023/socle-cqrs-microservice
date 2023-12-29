package com.oga.cqrs.query.infrastructure.user;


import com.oga.cqrs.command.events.user.*;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * An interface representing a consumer of events. Implementations of this interface can consume
 * different types of events based on the provided payload, which should match the expected type
 * for that implementation.
 */
public interface IUserEventConsummer {

    /**
     * Consume an event of type `CreateUserEvent` with the provided payload.
     *
     * @param event The event payload to be consumed.
     * @param ack The acknowledgment to be sent after the event is consumed.
     */

    void consume(@Payload UserCreated event, Acknowledgment ack);

    /**
     * Consume an event of type `UpdateUserEvent` with the provided payload.
     *
     * @param event The event payload to be consumed.
     * @param ack The acknowledgment to be sent after the event is consumed.
     */

    void consume(@Payload UserUpdated event, Acknowledgment ack);

    /**
     * Consume an event of type `DeleteUserEvent` with the provided payload.
     *
     * @param event The event payload to be consumed.
     * @param ack The acknowledgment to be sent after the event is consumed.
     */
    void consume(@Payload UserDeleted event, Acknowledgment ack);

    /**
     * Consume an event of type `UpdatePasswordEvent` with the provided payload.
     *
     * @param event The event payload to be consumed.
     * @param ack The acknowledgment to be sent after the event is consumed.
     */
    void consume(@Payload PasswordUpdated event, Acknowledgment ack);
    /**
     * Consume an event of type `UserCreatedByAdmin` with the provided payload.
     *
     * @param event The event payload to be consumed.
     * @param ack The acknowledgment to be sent after the event is consumed.
     */
    void consume(@Payload UserCreatedByAdmin event, Acknowledgment ack);
    /**
     * Consume an event of type `RolesAffected` with the provided payload.
     *
     * @param event The event payload to be consumed.
     * @param ack The acknowledgment to be sent after the event is consumed.
     */
    void consume(@Payload RolesAffected event, Acknowledgment ack);
}
