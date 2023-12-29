package com.oga.subscription.command.handlers;

import com.oga.subscription.command.commands.product.CreateProductCommand;
import com.oga.subscription.command.commands.product.DeleteProductCommand;
import com.oga.subscription.command.commands.product.UpdateProductCommand;

/**
 * Interface for handling different types of commands.
 */
public interface ProductCommandHandlerInterface {

    /**
     * Handles a create product command.
     *
     * @param createProductCommand The create product command to handle.
     */
    void handle(CreateProductCommand createProductCommand);
    /**
     * Handles an update product command.
     *
     * @param updateProductCommand The update product command to handle.
     */
    void handle(UpdateProductCommand updateProductCommand);
    /**
     * Handles a delete product command.
     *
     * @param deleteProductCommand The delete product command to handle.
     */
    void handle(DeleteProductCommand deleteProductCommand);

}
