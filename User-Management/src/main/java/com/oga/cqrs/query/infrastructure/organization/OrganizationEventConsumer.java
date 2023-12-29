package com.oga.cqrs.query.infrastructure.organization;


import com.oga.cqrs.command.events.group.GroupCreated;
import com.oga.cqrs.command.events.group.GroupDeleted;
import com.oga.cqrs.command.events.group.GroupUpdated;
import com.oga.cqrs.command.events.organizations.OrganizationCreated;
import com.oga.cqrs.query.events.group.IGroupEventHandler;
import com.oga.cqrs.query.events.organization.IOrganizationEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * Kafka consumer that listens to topics (events) and handles incoming messages by calling the appropriate EventHandler.
 */
@Service
public class OrganizationEventConsumer implements IOrganizationEventConsummer {

    private final IOrganizationEventHandler eventHandler;

    @Autowired
    public OrganizationEventConsumer(IOrganizationEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    /**
     * Kafka listener method that is invoked when a new message is received on the "GroupCreated" topic.
     * Calls the eventHandler's on() method to handle the incoming message, then acknowledges receipt of the message.
     *
     * @param event The GroupCreated message received from Kafka.
     * @param ack   The Kafka acknowledgment object that is used to confirm receipt of the message.
     */

    @KafkaListener(topics = "OrganizationCreated", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload OrganizationCreated event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }
}