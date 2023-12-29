package com.oga.cqrs.query.infrastructure.group;


import com.oga.cqrs.command.events.group.*;
import com.oga.cqrs.query.events.group.IGroupEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * Kafka consumer that listens to topics (events) and handles incoming messages by calling the appropriate EventHandler.
 */
@Service
public class GroupEventConsumer implements IGroupEventConsummer {

    private final IGroupEventHandler eventHandler;
    @Autowired
    public GroupEventConsumer( IGroupEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    /**
     * Kafka listener method that is invoked when a new message is received on the "GroupCreated" topic.
     * Calls the eventHandler's on() method to handle the incoming message, then acknowledges receipt of the message.
     *
     * @param event The GroupCreated message received from Kafka.
     * @param ack   The Kafka acknowledgment object that is used to confirm receipt of the message.
     */

    @KafkaListener(topics = "GroupCreated", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload GroupCreated event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }
    /**
     * Kafka listener method that is invoked when a new message is received on the "GroupUpdated" topic.
     * Calls the eventHandler's on() method to handle the incoming message, then acknowledges receipt of the message.
     *
     * @param event The GroupUpdated message received from Kafka.
     * @param ack   The Kafka acknowledgment object that is used to confirm receipt of the message.
     */
    @KafkaListener(topics = "GroupUpdated", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(GroupUpdated event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }
    /**
     * Kafka listener method that is invoked when a new message is received on the "GroupDeleted" topic.
     * Calls the eventHandler's on() method to handle the incoming message, then acknowledges receipt of the message.
     *
     * @param event The GroupDeleted message received from Kafka.
     * @param ack   The Kafka acknowledgment object that is used to confirm receipt of the message.
     */
    @KafkaListener(topics = "GroupDeleted", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(GroupDeleted event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }
}
