package com.oga.workflow.query.infrastructure;

import com.oga.workflow.command.events.impl.ReviewCreatedEvent;
import com.oga.workflow.command.events.impl.ReviewUpdatedEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumerReview {

    void consume(@Payload ReviewCreatedEvent event, Acknowledgment ack);
    void consume(@Payload ReviewUpdatedEvent event, Acknowledgment ack);

}
