package com.oga.subscription.command.domain;

import com.oga.subscription.command.commands.product.CreateProductCommand;
import com.oga.subscription.command.commands.product.DeleteProductCommand;
import com.oga.subscription.command.commands.product.UpdateProductCommand;
import com.oga.subscription.command.events.product.CreateProductEvent;
import com.oga.subscription.command.events.product.UpdateProductEvent;
import com.oga.subscription.command.rest.dto.Product;
import com.oga.cqrsref.domain.AggregateRoot;

/**
 * Represents a product aggregate in the domain.
 */
public class ProductAggregate extends AggregateRoot {
    private Product product;
    /**
     * Constructs a product aggregate with the given create product command.
     *
     * @param command The create product command.
     */
    public ProductAggregate(CreateProductCommand command) {
        raiseEvent(CreateProductEvent.builder()
                .identifier(command.getId())
                .category(command.getCategory())
                .type(command.getType())
                .name(command.getName())
                .build());
    }
    /**
     * Constructs a product aggregate with the given update product command.
     *
     * @param command The update product command.
     */
    public ProductAggregate(UpdateProductCommand command) {
        raiseEvent(UpdateProductEvent.builder()
                .identifier(command.getId())
                .category(command.getCategory())
                .type(command.getType())
                .name(command.getName())
                .build());
    }
    /**
     * Constructs a product aggregate with the given delete product command.
     *
     * @param command The delete product command.
     */
    public ProductAggregate(DeleteProductCommand command) {
            raiseEvent(UpdateProductEvent.builder()
                    .identifier(command.getId())
                    .build());
        }
    /**
     * Default constructor for the product aggregate.
     */
    public ProductAggregate() {
    }
    /**
     * Applies the create product event to update the aggregate state.
     *
     * @param event The create product event.
     */
    public void apply (CreateProductEvent event)
    {
        this.id=event.getIdentifier();

    }
    /**
     * Applies the update product event to update the aggregate state.
     *
     * @param event The update product event.
     */
    public void apply (UpdateProductEvent event)
    {
        this.id=event.getIdentifier();

    }

}

