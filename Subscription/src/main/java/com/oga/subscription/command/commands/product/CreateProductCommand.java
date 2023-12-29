package com.oga.subscription.command.commands.product;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.killbill.billing.catalog.api.ProductCategory;

/**
 * Represents a command to create a product.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductCommand  extends BaseCommand {
    private String type;
    private ProductCategory category;
    private String name;
}
