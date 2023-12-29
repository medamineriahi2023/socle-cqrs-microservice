package com.oga.cqrs.query.infrastructure.role;

import com.oga.cqrs.command.events.role.RoleCreated;
import com.oga.cqrs.command.events.role.RoleDeleted;
import com.oga.cqrs.command.events.role.RoleUpdated;
import com.oga.cqrs.query.events.role.IRoleEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class RoleEventConsummer implements IRoleEventConsummer{
  private final IRoleEventHandler eventHandler;
  @Autowired
  public RoleEventConsummer( IRoleEventHandler eventHandler) {
    this.eventHandler = eventHandler;
  }

  /**
   * Kafka listener method that is invoked when a new message is received on the "RoleCreated" topic.
   * Calls the eventHandler's on() method to handle the incoming message, then acknowledges receipt of the message.
   *
   * @param event The RoleCreated message received from Kafka.
   * @param ack   The Kafka acknowledgment object that is used to confirm receipt of the message.
   */

  @KafkaListener(topics = "RoleCreated", groupId = "${spring.kafka.consumer.group-id}")
  @Override
  public void consume(@Payload RoleCreated event, Acknowledgment ack) {
    this.eventHandler.on(event);
    ack.acknowledge();
  }
  /**
   * Kafka listener method that is invoked when a new message is received on the "RoleUpdated" topic.
   * Calls the eventHandler's on() method to handle the incoming message, then acknowledges receipt of the message.
   *
   * @param event The RoleUpdated message received from Kafka.
   * @param ack   The Kafka acknowledgment object that is used to confirm receipt of the message.
   */
  @KafkaListener(topics = "RoleUpdated", groupId = "${spring.kafka.consumer.group-id}")
  @Override
  public void consume(RoleUpdated event, Acknowledgment ack) {
    this.eventHandler.on(event);
    ack.acknowledge();
  }

  /**
   * Kafka listener method that is invoked when a new message is received on the "RoleDeleted" topic.
   * Calls the eventHandler's on() method to handle the incoming message, then acknowledges receipt of the message.
   *
   * @param event The RoleDeleted message received from Kafka.
   * @param ack   The Kafka acknowledgment object that is used to confirm receipt of the message.
   */
  @KafkaListener(topics = "RoleDeleted", groupId = "${spring.kafka.consumer.group-id}")
  @Override
  public void consume(RoleDeleted event, Acknowledgment ack) {
    this.eventHandler.on(event);
    ack.acknowledge();
  }

}
