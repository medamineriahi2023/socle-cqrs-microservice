package com.oga.subscription.command.commands.catalog;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a command to create a plan.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCatalogCommand extends BaseCommand {
    private String planId ;
    private String identifier;

}
