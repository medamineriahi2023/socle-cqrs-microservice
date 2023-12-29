package com.oga.subscription.command.handlers;

import com.oga.subscription.command.commands.subscription.CreateSubscriptionCommand;
import org.killbill.billing.client.KillBillClientException;

/**
 * Interface for handling different types of commands.
 */
public interface SubscriptionCommandHandlerInterface {

    void handle(CreateSubscriptionCommand createSubscriptionCommand) throws KillBillClientException;
}
