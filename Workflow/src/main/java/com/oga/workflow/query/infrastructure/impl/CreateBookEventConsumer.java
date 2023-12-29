package com.oga.workflow.query.infrastructure.impl;

import com.oga.workflow.command.events.impl.BookCreatedEvent;
import com.oga.workflow.query.event.impl.BookEventsHandler;
import com.oga.workflow.query.infrastructure.EventConsumerBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class CreateBookEventConsumer implements EventConsumerBook {

     @Autowired
     BookEventsHandler createBookEvent;

    @KafkaListener(topics = "BookCreatedEvent",groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload BookCreatedEvent event, Acknowledgment ack) {

        // Access and process the eventModel as needed
        createBookEvent.on(event);

    }



}
