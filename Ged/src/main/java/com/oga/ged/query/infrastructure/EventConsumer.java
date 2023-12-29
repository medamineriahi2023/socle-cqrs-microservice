package com.oga.ged.query.infrastructure;

import com.oga.ged.command.events.*;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * Consumer for events.
 */
public interface EventConsumer {

    /**
     * Consumes the FolderCreatedEvent.
     *
     * @param event     The FolderCreatedEvent to consume.
     * @param ack       The Acknowledgment object to acknowledge the event processing.
     */
    void consume(@Payload FolderCreatedEvent event, Acknowledgment ack);

    /**
     * Consumes the FolderUpdatedEvent.
     *
     * @param event     The FolderUpdatedEvent to consume.
     * @param ack       The Acknowledgment object to acknowledge the event processing.
     */
    void consume(@Payload FolderUpdatedEvent event, Acknowledgment ack);

    /**
     * Consumes the FolderDeletedEvent.
     *
     * @param event     The FolderDeletedEvent to consume.
     * @param ack       The Acknowledgment object to acknowledge the event processing.
     */
    void consume(@Payload FolderDeletedEvent event, Acknowledgment ack);

    /**
     * Consumes the FolderMovedEvent.
     *
     * @param event     The FolderMovedEvent to consume.
     * @param ack       The Acknowledgment object to acknowledge the event processing.
     */
    void consume(@Payload FolderMovedEvent event, Acknowledgment ack);

    /**
     * Consumes the FileCreatedEvent.
     *
     * @param event     The FileCreatedEvent to consume.
     * @param ack       The Acknowledgment object to acknowledge the event processing.
     */
    void consume(@Payload FileCreatedEvent event, Acknowledgment ack);

    /**
     * Consumes the FileUpdatedEvent.
     *
     * @param event     The FileUpdatedEvent to consume.
     * @param ack       The Acknowledgment object to acknowledge the event processing.
     */
    void consume(@Payload FileUpdatedEvent event, Acknowledgment ack);

    /**
     * Consumes the FileDeletedEvent.
     *
     * @param event     The FileDeletedEvent to consume.
     * @param ack       The Acknowledgment object to acknowledge the event processing.
     */
    void consume(@Payload FileDeletedEvent event, Acknowledgment ack);

    /**
     * Consumes the FileMovedEvent.
     *
     * @param event     The FileMovedEvent to consume.
     * @param ack       The Acknowledgment object to acknowledge the event processing.
     */
    void consume(@Payload FileMovedEvent event, Acknowledgment ack);

    /**
     * Consumes the UserCreatedEvent.
     *
     * @param event     The UserCreatedEvent to consume.
     * @param ack       The Acknowledgment object to acknowledge the event processing.
     */
    void consume(@Payload UserCreatedEvent event, Acknowledgment ack);

    /**
     * Consumes the UserUpdatedEvent.
     *
     * @param event     The UserUpdatedEvent to consume.
     * @param ack       The Acknowledgment object to acknowledge the event processing.
     */
    void consume(@Payload UserUpdatedEvent event, Acknowledgment ack);

    /**
     * Consumes the GroupCreatedEvent.
     *
     * @param event The GroupCreatedEvent to consume.
     * @param ack The Acknowledgment object to acknowledge the event processing.
     */
    void consume(@Payload GroupCreatedEvent event, Acknowledgment ack);

    /**
     * Consumes the GroupDeletedEvent.
     *
     * @param event The GroupDeletedEvent to consume.
     * @param ack The Acknowledgment object to acknowledge the event processing.
     */
    void consume(@Payload GroupDeletedEvent event, Acknowledgment ack);

    /**
     * Consumes the PersonAssignedToGroupEvent.
     *
     * @param event The PersonAssignedToGroupEvent to consume.
     * @param ack The Acknowledgment object to acknowledge the event processing.
     */
    void consume(@Payload PersonAssignedToGroupEvent event, Acknowledgment ack);

    /**
     * Consumes the PersonRemovedFromGroupEvent.
     *
     * @param event The PersonRemovedFromGroupEvent to consume.
     * @param ack The Acknowledgment object to acknowledge the event processing.
     */
    void consume(@Payload PersonRemovedFromGroupEvent event, Acknowledgment ack);

    /**
     * Consumes the SharedLinkCreatedEvent.
     *
     * @param event The SharedLinkCreatedEvent to consume.
     * @param ack The Acknowledgment object to acknowledge the event processing.
     */
    void consume(@Payload SharedLinkCreatedEvent event, Acknowledgment ack);

    /**
     * Consumes the SharedLinkDeletedEvent.
     *
     * @param event The SharedLinkDeletedEvent to consume.
     * @param ack The Acknowledgment object to acknowledge the event processing.
     */
    void consume(@Payload SharedLinkDeletedEvent event, Acknowledgment ack);
}
