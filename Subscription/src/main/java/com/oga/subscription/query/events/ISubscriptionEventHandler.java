package com.oga.subscription.query.events;

import com.oga.subscription.command.events.subscription.CreateSubscriptionEvent;
import org.springframework.stereotype.Service;

/**
 * This interface represents an event handler for handling events.
 */
@Service
public interface ISubscriptionEventHandler {

    /**
     * Handles the given create product event.
     *
     * @param event the create product event to handle
     */
    void on(CreateSubscriptionEvent event);
}
