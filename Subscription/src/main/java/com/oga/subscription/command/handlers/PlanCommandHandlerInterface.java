package com.oga.subscription.command.handlers;

import com.oga.subscription.command.commands.catalog.CreatePlanCommand;
import com.oga.subscription.command.commands.catalog.DeleteCatalogCommand;
import org.killbill.billing.client.KillBillClientException;

/**
 * Interface for handling different types of commands.
 */
public interface PlanCommandHandlerInterface {


    void handle(CreatePlanCommand createPlanCommand) throws KillBillClientException;
    void handle(DeleteCatalogCommand createPlanCommand) throws KillBillClientException;
}
