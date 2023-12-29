package com.oga.subscription.query.events;

import com.oga.subscription.command.events.product.CreateProductEvent;
import com.oga.subscription.command.events.product.DeleteProductEvent;
import com.oga.subscription.command.events.product.UpdateProductEvent;
import org.springframework.stereotype.Service;

/**
 * This interface represents an event handler for handling events.
 */
@Service
public interface IProductEventHandler {

    /**
     * Handles the given create product event.
     *
     * @param event the create product event to handle
     */
    void on(CreateProductEvent event);
    /**
     * Handles the given update product event.
     *
     * @param event The update product event to handle.
     */
    void on(UpdateProductEvent event);
    /**
     * Handles the given delete product event.
     *
     * @param event The delete product event to handle.
     */
    void on(DeleteProductEvent event);
}
