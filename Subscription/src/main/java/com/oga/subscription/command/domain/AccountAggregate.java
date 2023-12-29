package com.oga.subscription.command.domain;

import com.oga.cqrsref.domain.AggregateRoot;
import com.oga.subscription.command.commands.account.CreateAccountCommand;
import com.oga.subscription.command.commands.catalog.DeleteCatalogCommand;
import com.oga.subscription.command.events.account.CreateAccountEvent;
import org.killbill.billing.client.model.gen.Account;

/**
 * Represents a plan aggregate in the domain.
 */
public class AccountAggregate extends AggregateRoot {
    private Account account;

    /**
     * Constructs a plan aggregate with the given create plan command.
     *
     * @param command The create plan command.
     */
    public AccountAggregate(CreateAccountCommand command) {
        raiseEvent(CreateAccountEvent.builder()
                .identifier(command.getId())
                .accountId(command.getAccountId())
                .build());
    }

    /**
     * Default constructor for the plan aggregate.
     */
    public AccountAggregate() {
    }

    /**
     * Applies the create plan event to update the aggregate state.
     *
     * @param event The create plan event.
     */
    public void apply(CreateAccountEvent event) {
        this.id = event.getIdentifier();

    }

    public void apply(DeleteCatalogCommand event) {
        this.id = event.getIdentifier();

    }

}

