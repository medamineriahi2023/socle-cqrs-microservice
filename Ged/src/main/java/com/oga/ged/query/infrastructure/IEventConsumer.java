package com.oga.ged.query.infrastructure;

import com.oga.ged.command.events.*;
import com.oga.ged.query.events.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * Implementation of the EventConsumer interface that consumes events from Kafka topics.
 */
@Service
public class IEventConsumer implements EventConsumer {
    private final EventHandler eventHandler;
    @Autowired
    public IEventConsumer(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    /**
     * Consumes the FolderCreatedEvent from the Kafka topic "FolderCreatedEvent".
     *
     * @param event     The FolderCreatedEvent to consume.
     * @param ack       The Acknowledgment object to acknowledge the event processing.
     */
    @KafkaListener(topics = "FolderCreatedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload FolderCreatedEvent event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }

    /**
     * Consumes the FolderUpdatedEvent from the Kafka topic "FolderUpdatedEvent".
     *
     * @param event     The FolderUpdatedEvent to consume.
     * @param ack       The Acknowledgment object to acknowledge the event processing.
     */
    @KafkaListener(topics = "FolderUpdatedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload FolderUpdatedEvent event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }

    /**
     * Consumes the FolderDeletedEvent from the Kafka topic "FolderDeletedEvent".
     *
     * @param event     The FolderDeletedEvent to consume.
     * @param ack       The Acknowledgment object to acknowledge the event processing.
     */
    @KafkaListener(topics = "FolderDeletedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload FolderDeletedEvent event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }

    /**
     * Consumes the FolderMovedEvent from the Kafka topic "FolderMovedEvent".
     *
     * @param event     The FolderMovedEvent to consume.
     * @param ack       The Acknowledgment object to acknowledge the event processing.
     */
    @KafkaListener(topics = "FolderMovedEvent", groupId = "${spring.kafka.consumer.group-id}")
     @Override
    public void consume(@Payload FolderMovedEvent event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }

    /**
     * Consumes the FileCreatedEvent from the Kafka topic "FileCreatedEvent".
     *
     * @param event     The FileCreatedEvent to consume.
     * @param ack       The Acknowledgment object to acknowledge the event processing.
     */
    @KafkaListener(topics = "FileCreatedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload FileCreatedEvent event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }

    /**
     * Consumes the FileUpdatedEvent from the Kafka topic "FileUpdatedEvent".
     *
     * @param event     The FileUpdatedEvent to consume.
     * @param ack       The Acknowledgment object to acknowledge the event processing.
     */
    @KafkaListener(topics = "FileUpdatedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload FileUpdatedEvent event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }

    /**
     * Consumes the FileDeletedEvent from the Kafka topic "FileDeletedEvent".
     *
     * @param event     The FileDeletedEvent to consume.
     * @param ack       The Acknowledgment object to acknowledge the event processing.
     */
    @KafkaListener(topics = "FileDeletedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload FileDeletedEvent event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }

    /**
     * Consumes the FileMovedEvent from the Kafka topic "FileMovedEvent".
     *
     * @param event     The FileMovedEvent to consume.
     * @param ack       The Acknowledgment object to acknowledge the event processing.
     */
    @KafkaListener(topics = "FileMovedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload FileMovedEvent event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }

    /**
     * Consumes the UserCreatedEvent from the Kafka topic "UserCreatedEvent".
     *
     * @param event     The UserCreatedEvent to consume.
     * @param ack       The Acknowledgment object to acknowledge the event processing.
     */
    @KafkaListener(topics = "UserCreatedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload UserCreatedEvent event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }

    /**
     * Consumes the UserUpdatedEvent from the Kafka topic "UserUpdatedEvent".
     *
     * @param event     The UserUpdatedEvent to consume.
     * @param ack       The Acknowledgment object to acknowledge the event processing.
     */
    @KafkaListener(topics = "UserUpdatedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload UserUpdatedEvent event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }

    /**
     * Consumes the GroupCreatedEvent.
     *
     * @param event The GroupCreatedEvent to consume.
     * @param ack The Acknowledgment object to acknowledge the event processing.
     */
    @KafkaListener(topics = "GroupCreatedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload GroupCreatedEvent event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }

    /**
     * Consumes the GroupDeletedEvent.
     *
     * @param event The GroupDeletedEvent to consume.
     * @param ack The Acknowledgment object to acknowledge the event processing.
     */
    @KafkaListener(topics = "GroupDeletedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload GroupDeletedEvent event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }

    /**
     * Consumes the PersonAssignedToGroupEvent.
     *
     * @param event The PersonAssignedToGroupEvent to consume.
     * @param ack The Acknowledgment object to acknowledge the event processing.
     */
    @KafkaListener(topics = "PersonAssignedToGroupEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload PersonAssignedToGroupEvent event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }

    /**
     * Consumes the PersonRemovedFromGroupEvent.
     *
     * @param event The PersonRemovedFromGroupEvent to consume.
     * @param ack The Acknowledgment object to acknowledge the event processing.
     */
    @KafkaListener(topics = "PersonRemovedFromGroupEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload PersonRemovedFromGroupEvent event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }

    /**
     * Consumes the SharedLinkCreatedEvent.
     *
     * @param event The SharedLinkCreatedEvent to consume.
     * @param ack The Acknowledgment object to acknowledge the event processing.
     */
    @KafkaListener(topics = "SharedLinkCreatedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload SharedLinkCreatedEvent event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }

    /**
     * Consumes the SharedLinkDeletedEvent.
     *
     * @param event The SharedLinkDeletedEvent to consume.
     * @param ack The Acknowledgment object to acknowledge the event processing.
     */
    @KafkaListener(topics = "SharedLinkDeletedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload SharedLinkDeletedEvent event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }

}
