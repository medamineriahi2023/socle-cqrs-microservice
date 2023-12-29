package com.oga.subscription.command.domain;

import com.oga.cqrsref.domain.AggregateRoot;
import com.oga.subscription.command.commands.subscription.CreateSubscriptionCommand;
import com.oga.subscription.command.events.subscription.CreateSubscriptionEvent;
import org.killbill.billing.client.model.gen.Subscription;

public class SubscriptionAggregate extends AggregateRoot {
    private Subscription subscription;
    /**
     * Constructs a subscription aggregate with the given create subscription command.
     *
     * @param command The create subscription command.
     */
    public SubscriptionAggregate(CreateSubscriptionCommand command) {
        raiseEvent(CreateSubscriptionEvent.builder()
                .identifier(command.getId())
                .build());
    }
    /**
     * Default constructor for the subscription aggregate.
     */
    public SubscriptionAggregate() {
    }
    /**
     * Applies the create subscription event to update the aggregate state.
     *
     * @param event The create subscription event.
     */
    public void apply (CreateSubscriptionEvent event)
    {
        this.id=event.getIdentifier();

    }

}

