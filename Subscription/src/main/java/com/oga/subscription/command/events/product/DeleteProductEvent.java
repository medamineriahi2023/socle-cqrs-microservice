package com.oga.subscription.command.events.product;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents an event indicating the deletion of a product.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class DeleteProductEvent extends BaseEvent {
    private String identifier;
}
