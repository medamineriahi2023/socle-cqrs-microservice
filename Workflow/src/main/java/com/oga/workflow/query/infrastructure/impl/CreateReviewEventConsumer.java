package com.oga.workflow.query.infrastructure.impl;


import com.oga.workflow.command.events.impl.ReviewCreatedEvent;
import com.oga.workflow.command.events.impl.ReviewUpdatedEvent;
import com.oga.workflow.query.event.impl.ReviewEventsHandler;
import com.oga.workflow.query.infrastructure.EventConsumerReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


@Service
public class CreateReviewEventConsumer implements EventConsumerReview {
    @Autowired
    ReviewEventsHandler createReviewEvent;
    @KafkaListener(topics = "ReviewCreatedEvent",groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload ReviewCreatedEvent event, Acknowledgment ack) {


        // Access and process the eventModel as needed
        createReviewEvent.on(event);
    }
    @KafkaListener(topics = "ReviewUpdatedEvent",groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload ReviewUpdatedEvent event, Acknowledgment ack) {


        // Access and process the eventModel as needed
        createReviewEvent.on(event);
    }


}
