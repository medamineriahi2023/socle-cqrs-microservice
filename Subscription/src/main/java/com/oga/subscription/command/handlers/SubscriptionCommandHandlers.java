package com.oga.subscription.command.handlers;

import com.oga.cqrsref.domain.AggregateRoot;
import com.oga.cqrsref.handlers.EventSourcingHandler;
import com.oga.subscription.command.commands.subscription.CreateSubscriptionCommand;
import com.oga.subscription.command.config.KillBillClientProperties;
import com.oga.subscription.command.domain.SubscriptionAggregate;
import com.oga.subscription.command.rest.exception.CustomSubscriptionException;
import org.joda.time.LocalDate;
import org.killbill.billing.client.KillBillClientException;
import org.killbill.billing.client.api.gen.SubscriptionApi;
import org.killbill.billing.client.model.gen.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;


/**
    This class represents the implementation of the CommandHandlerInterface that handles
    incoming commands .
 */

@Service
@ComponentScan("com.oga.cqrsref.handlers")
public class SubscriptionCommandHandlers implements SubscriptionCommandHandlerInterface {

    private final EventSourcingHandler<AggregateRoot>  eventSourcingHandler;

    private final KillBillClientProperties properties;




    /**
     * Constructs a CommandHandlers instance.
     *
     * @param eventSourcingHandler The event sourcing handler to use for saving the root aggregate.
     * @param properties
     */
    @Autowired
    public SubscriptionCommandHandlers(EventSourcingHandler<AggregateRoot> eventSourcingHandler, KillBillClientProperties properties) {
        this.eventSourcingHandler = eventSourcingHandler;
        this.properties = properties;
    }
    @Override
    public void handle(CreateSubscriptionCommand createSubscriptionCommand)  {
        SubscriptionAggregate subscriptionAggregate = new SubscriptionAggregate(createSubscriptionCommand);
        SubscriptionApi subscriptionApi = new SubscriptionApi(properties.getHttpClient());

        Subscription body = new Subscription();
        body.setAccountId(createSubscriptionCommand.getAccountId());
        body.setPlanName(createSubscriptionCommand.getPlanName());


        try {
            Subscription subscription = subscriptionApi.createSubscription(body,
                    LocalDate.now(),
                    LocalDate.now(),
                    null,
                    properties.getRequestOptions());
        } catch (KillBillClientException e) {
            throw new CustomSubscriptionException("An error occurred while creating the subscription.", e);
        }
        eventSourcingHandler.save(subscriptionAggregate);
    }



}
