package com.oga.subscription.command.commands.product;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents a command to delete a product.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class DeleteProductCommand extends BaseCommand {
    }
