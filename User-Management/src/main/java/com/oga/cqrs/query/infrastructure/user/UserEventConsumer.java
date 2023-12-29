package com.oga.cqrs.query.infrastructure.user;


import com.oga.cqrs.command.events.user.*;
import com.oga.cqrs.query.events.user.IUserEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
/**
 * Kafka consumer that listens to topics (events) and handles incoming messages by calling the appropriate EventHandler.
 */
@Service
public class UserEventConsumer implements IUserEventConsummer {

    private final IUserEventHandler eventHandler;
    @Autowired
    public UserEventConsumer(IUserEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    /**
     * Kafka listener method that is invoked when a new message is received on the "CreateUserEvent" topic.
     * Calls the eventHandler's on() method to handle the incoming message, then acknowledges receipt of the message.
     *
     * @param event The CreateUserEvent message received from Kafka.
     * @param ack   The Kafka acknowledgment object that is used to confirm receipt of the message.
     */

    @KafkaListener(topics = "UserCreated", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload UserCreated event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }


    /**
     * Kafka listener method that is invoked when a new message is received on the "UpdateUserEvent" topic.
     * Calls the eventHandler's on() method to handle the incoming message, then acknowledges receipt of the message.
     *
     * @param event The UserUpdated message received from Kafka.
     * @param ack   The Kafka acknowledgment object that is used to confirm receipt of the message.
     */
    @KafkaListener(topics = "UserUpdated", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload UserUpdated event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }
    /**
     * Kafka listener method that is invoked when a new message is received on the "DeleteUserEvent" topic.
     * Calls the eventHandler's on() method to handle the incoming message, then acknowledges receipt of the message.
     *
     * @param event The DeleteUserEvent message received from Kafka.
     * @param ack   The Kafka acknowledgment object that is used to confirm receipt of the message.
     */
    @KafkaListener(topics = "UserDeleted", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload UserDeleted event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }
    /**
     * Kafka listener method that is invoked when a new message is received on the "UserCreatedByAdmin" topic.
     * Calls the eventHandler's on() method to handle the incoming message, then acknowledges receipt of the message.
     *
     * @param event The UserCreatedByAdmin message received from Kafka.
     * @param ack   The Kafka acknowledgment object that is used to confirm receipt of the message.
     */
    @KafkaListener(topics = "UserCreatedByAdmin", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload UserCreatedByAdmin event, Acknowledgment ack) {
        System.out.println("consumer");
        this.eventHandler.on(event);
        ack.acknowledge();
    }



    /**
     * Kafka listener method that is invoked when a new message is received on the "UpdatePasswordEvent" topic.
     * Calls the eventHandler's on() method to handle the incoming message, then acknowledges receipt of the message.
     *
     * @param event The CreateUserEvent message received from Kafka.
     * @param ack   The Kafka acknowledgment object that is used to confirm receipt of the message.
     */
    @KafkaListener(topics = "PasswordUpdated", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload PasswordUpdated event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }

    /**
     * Kafka listener method that is invoked when a new message is received on the "RolesAffected" topic.
     * Calls the eventHandler's on() method to handle the incoming message, then acknowledges receipt of the message.
     *
     * @param event The RolesAffected message received from Kafka.
     * @param ack   The Kafka acknowledgment object that is used to confirm receipt of the message.
     */
    @KafkaListener(topics = "RolesAffected", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload RolesAffected event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }
}
