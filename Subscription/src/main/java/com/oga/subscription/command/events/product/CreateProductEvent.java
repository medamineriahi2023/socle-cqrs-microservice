package com.oga.subscription.command.events.product;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.killbill.billing.catalog.api.ProductCategory;

/**
 * Represents an event indicating the creation of a product.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class CreateProductEvent extends BaseEvent {
    private String identifier;
    private ProductCategory category;
    private String type;
    private String name;
}
