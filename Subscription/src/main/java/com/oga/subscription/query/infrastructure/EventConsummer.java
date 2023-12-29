package com.oga.subscription.query.infrastructure;

import com.oga.subscription.command.events.catalog.CreatePlanEvent;
import com.oga.subscription.command.events.product.CreateProductEvent;
import com.oga.subscription.command.events.product.DeleteProductEvent;
import com.oga.subscription.command.events.product.UpdateProductEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * An interface representing a consumer of events. Implementations of this interface can consume
 * different types of events based on the provided payload, which should match the expected type
 * for that implementation.
 */
public interface EventConsummer {

    /**
     * Consume an event of type `CreateEvent` with the provided payload.
     *
     * @param event The event payload to be consumed.
     * @param ack The acknowledgment to be sent after the event is consumed.
     */
    void consume(@Payload CreateProductEvent event, Acknowledgment ack);
    void consume(@Payload UpdateProductEvent event, Acknowledgment ack);
    void consume(@Payload DeleteProductEvent event, Acknowledgment ack);
    void consume(@Payload CreatePlanEvent event, Acknowledgment ack);
}
