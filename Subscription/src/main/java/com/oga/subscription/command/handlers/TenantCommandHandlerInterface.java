package com.oga.subscription.command.handlers;

import com.oga.subscription.command.commands.tenant.CreateTenantCommand;
import org.killbill.billing.client.KillBillClientException;

/**
 * Interface for handling different types of commands.
 */
public interface TenantCommandHandlerInterface {


    void handle(CreateTenantCommand createTenantCommand) throws KillBillClientException;
}
