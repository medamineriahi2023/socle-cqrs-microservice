package com.oga.cqrsref.producer;



import com.oga.cqrsref.events.BaseEvent;
import org.springframework.stereotype.Component;
@Component
public interface EventProducer {
    void produce(String topic, BaseEvent event);
}
