package com.oga.subscription.command.domain;

import com.oga.subscription.command.commands.catalog.CreatePlanCommand;
import com.oga.subscription.command.commands.catalog.DeleteCatalogCommand;
import com.oga.subscription.command.events.catalog.CreatePlanEvent;
import com.oga.cqrsref.domain.AggregateRoot;
import org.killbill.billing.client.model.gen.SimplePlan;

/**
 * Represents a plan aggregate in the domain.
 */
public class PlanAggregate extends AggregateRoot {
    private SimplePlan plan;
    /**
     * Constructs a plan aggregate with the given create plan command.
     *
     * @param command The create plan command.
     */
    public PlanAggregate(CreatePlanCommand command) {
        raiseEvent(CreatePlanEvent.builder()
                .identifier(command.getId())
                .planId(command.getPlanId())
                .amount(command.getAmount())
                .currency(command.getCurrency())
                .productCategory(command.getProductCategory())
                .productName(command.getProductName())
                .billingPeriod(command.getBillingPeriod())
                .trialLength(command.getTrialLength())
                .trialTimeUnit(command.getTrialTimeUnit())
                .availableBaseProducts(command.getAvailableBaseProducts())
                .build());
    }
    public PlanAggregate(DeleteCatalogCommand command) {
        raiseEvent(CreatePlanEvent.builder()
                .identifier(command.getId())
                .planId(command.getPlanId())
                .build());
    }
    /**
     * Default constructor for the plan aggregate.
     */
    public PlanAggregate() {
    }
    /**
     * Applies the create plan event to update the aggregate state.
     *
     * @param event The create plan event.
     */
    public void apply (CreatePlanEvent event)
    {
        this.id=event.getIdentifier();

    }    public void apply (DeleteCatalogCommand event)
    {
        this.id=event.getIdentifier();

    }

}

