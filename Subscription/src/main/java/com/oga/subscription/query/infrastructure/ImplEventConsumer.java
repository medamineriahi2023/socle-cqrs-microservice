package com.oga.subscription.query.infrastructure;

import com.oga.subscription.command.events.catalog.CreatePlanEvent;
import com.oga.subscription.command.events.product.CreateProductEvent;
import com.oga.subscription.command.events.product.DeleteProductEvent;
import com.oga.subscription.command.events.product.UpdateProductEvent;
import com.oga.subscription.query.events.IPlanEventHandler;
import com.oga.subscription.query.events.IProductEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
/**
 * Kafka consumer that listens to topics (events) and handles incoming messages by calling the appropriate EventHandler.
 */
@Service
public class ImplEventConsumer implements EventConsummer {

    private final IProductEventHandler productEventHandler;
    private final IPlanEventHandler planEventHandler;
    @Autowired
    public ImplEventConsumer(@Qualifier("IProductEventHandler") IProductEventHandler productEventHandler, @Qualifier("IPlanEventHandler") IPlanEventHandler planEventHandler) {
        this.productEventHandler = productEventHandler;
        this.planEventHandler = planEventHandler;
    }


    @KafkaListener(topics = "CreateProductEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload CreateProductEvent event, Acknowledgment ack) {
        this.productEventHandler.on(event);
        ack.acknowledge();
    }
    @KafkaListener(topics = "UpdateProductEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload UpdateProductEvent event, Acknowledgment ack) {
        this.productEventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "DeleteProductEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(DeleteProductEvent event, Acknowledgment ack) {
        this.productEventHandler.on(event);
        ack.acknowledge();
    }

    @Override
    @KafkaListener(topics = "CreatePlanEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(CreatePlanEvent event, Acknowledgment ack) {
        this.planEventHandler.on(event);
        ack.acknowledge();
    }
}
