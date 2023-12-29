package com.oga.workflow.query.infrastructure;

import com.oga.workflow.command.events.impl.BookCreatedEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumerBook {
   void consume(@Payload BookCreatedEvent event, Acknowledgment ack);
}
