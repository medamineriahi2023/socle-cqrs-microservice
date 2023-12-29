package com.oga.subscription.command.commands.product;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.killbill.billing.catalog.api.ProductCategory;
/**
 * Represents a command to update a product.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductCommand extends BaseCommand {
    private String type;
    private ProductCategory category;
    private String name;
}
