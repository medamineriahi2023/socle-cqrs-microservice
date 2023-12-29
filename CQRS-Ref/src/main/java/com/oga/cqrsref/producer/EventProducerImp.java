package com.oga.cqrsref.producer;

import com.oga.cqrsref.events.BaseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


/**
 * Service class responsible for producing events to a Kafka topic.
 */
@Service
public class EventProducerImp implements EventProducer {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Produces the given event to the specified Kafka topic.
     *
     * @param topic the Kafka topic to produce the event to
     * @param event the event to produce
     */
    @Override
    public void produce(String topic, BaseEvent event) {
        this.kafkaTemplate.send(topic, event);
    }

}
