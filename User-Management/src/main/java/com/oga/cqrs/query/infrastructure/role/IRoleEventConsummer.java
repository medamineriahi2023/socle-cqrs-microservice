package com.oga.cqrs.query.infrastructure.role;

import com.oga.cqrs.command.events.role.RoleCreated;
import com.oga.cqrs.command.events.role.RoleDeleted;
import com.oga.cqrs.command.events.role.RoleUpdated;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface IRoleEventConsummer {
  /**
   * Consume an event of type `RoleCreated` with the provided payload.
   *
   * @param event The event payload to be consumed.
   * @param ack The acknowledgment to be sent after the event is consumed.
   */

  void consume(@Payload RoleCreated event, Acknowledgment ack);
  /**
   * Consume an event of type `RoleUpdated` with the provided payload.
   *
   * @param event The event payload to be consumed.
   * @param ack The acknowledgment to be sent after the event is consumed.
   */

  void consume(@Payload RoleUpdated event, Acknowledgment ack);
  /**
   * Consume an event of type `RoleDeleted` with the provided payload.
   *
   * @param event The event payload to be consumed.
   * @param ack The acknowledgment to be sent after the event is consumed.
   */

  void consume(@Payload RoleDeleted event, Acknowledgment ack);
}
