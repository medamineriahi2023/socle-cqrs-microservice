package com.oga.subscription.command.domain;

import com.oga.cqrsref.domain.AggregateRoot;
import com.oga.subscription.command.commands.tenant.CreateTenantCommand;
import com.oga.subscription.command.events.catalog.CreateTenantEvent;
import org.killbill.billing.client.model.gen.Tenant;

public class TenantAggregate extends AggregateRoot {
    private Tenant tenant;
    /**
     * Constructs a tenant aggregate with the given create tenant command.
     *
     * @param command The create tenant command.
     */
    public TenantAggregate(CreateTenantCommand command) {
        raiseEvent(CreateTenantEvent.builder()
                .identifier(command.getId())
                .build());
    }
    /**
     * Default constructor for the tenant aggregate.
     */
    public TenantAggregate() {
    }
    /**
     * Applies the create tenant event to update the aggregate state.
     *
     * @param event The create tenant event.
     */
    public void apply (CreateTenantEvent event)
    {
        this.id=event.getIdentifier();

    }

}

