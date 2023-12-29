package com.oga.subscription.command.handlers;

import com.oga.cqrsref.domain.AggregateRoot;
import com.oga.cqrsref.handlers.EventSourcingHandler;
import com.oga.subscription.command.commands.catalog.CreatePlanCommand;
import com.oga.subscription.command.commands.catalog.DeleteCatalogCommand;
import com.oga.subscription.command.config.KillBillClientProperties;
import com.oga.subscription.command.domain.PlanAggregate;
import org.killbill.billing.client.KillBillClientException;
import org.killbill.billing.client.RequestOptions;
import org.killbill.billing.client.api.gen.CatalogApi;
import org.killbill.billing.client.model.gen.SimplePlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;


/**
    This class represents the implementation of the CommandHandlerInterface that handles
    incoming commands .
 */

@Service
@ComponentScan("com.oga.cqrsref.handlers")
public class PlanCommandHandlers implements PlanCommandHandlerInterface {

    private final EventSourcingHandler<AggregateRoot>  eventSourcingHandler;

    private final KillBillClientProperties properties;




    /**
     * Constructs a CommandHandlers instance.
     *
     * @param eventSourcingHandler The event sourcing handler to use for saving the root aggregate.
     * @param properties
     */
    @Autowired
    public PlanCommandHandlers(EventSourcingHandler<AggregateRoot> eventSourcingHandler, KillBillClientProperties properties) {
        this.eventSourcingHandler = eventSourcingHandler;
        this.properties = properties;
    }
    @Override
    public void handle(CreatePlanCommand createPlanCommand) {
        CatalogApi catalogApi = new CatalogApi(properties.getHttpClient());
        PlanAggregate planAggregate = new PlanAggregate(createPlanCommand);
        SimplePlan body = new SimplePlan();
        body.setPlanId(createPlanCommand.getPlanId());
        body.setProductName(createPlanCommand.getProductName());
        body.setProductCategory(createPlanCommand.getProductCategory());
        body.setCurrency(createPlanCommand.getCurrency());
        body.setAmount(createPlanCommand.getAmount());
        body.setBillingPeriod(createPlanCommand.getBillingPeriod());
        body.setTrialLength(createPlanCommand.getTrialLength());
        body.setTrialTimeUnit(createPlanCommand.getTrialTimeUnit());

        RequestOptions requestOptions = properties.getRequestOptions();
        try {
            catalogApi.addSimplePlan(body, requestOptions);
        } catch (KillBillClientException e) {
            throw new RuntimeException("Failed to add a plan to the catalog: " + e.getMessage(), e);
        }
        eventSourcingHandler.save(planAggregate);
    }


    @Override
    public void handle(DeleteCatalogCommand deleteCatalogCommand) {
        CatalogApi catalogApi = new CatalogApi(properties.getHttpClient());

        PlanAggregate planAggregate = new PlanAggregate(deleteCatalogCommand);
        try {
            catalogApi.deleteCatalog(properties.getRequestOptions());
        } catch (KillBillClientException e) {
            throw new RuntimeException(e);
        }
        eventSourcingHandler.save(planAggregate);

    }

}
