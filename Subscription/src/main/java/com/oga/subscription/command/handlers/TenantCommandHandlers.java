package com.oga.subscription.command.handlers;

import com.oga.cqrsref.domain.AggregateRoot;
import com.oga.cqrsref.handlers.EventSourcingHandler;
import com.oga.subscription.command.commands.tenant.CreateTenantCommand;
import com.oga.subscription.command.config.KillBillClientProperties;
import com.oga.subscription.command.domain.TenantAggregate;
import org.killbill.billing.client.KillBillClientException;
import org.killbill.billing.client.api.gen.TenantApi;
import org.killbill.billing.client.model.gen.AuditLog;
import org.killbill.billing.client.model.gen.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;


/**
    This class represents the implementation of the CommandHandlerInterface that handles
    incoming commands .
 */

@Service
@ComponentScan("com.oga.cqrsref.handlers")
@ComponentScan("com.oga.subscription.command.config")
public class TenantCommandHandlers implements TenantCommandHandlerInterface {

    private final EventSourcingHandler<AggregateRoot>  eventSourcingHandler;


    private final KillBillClientProperties properties;
    /**
     * Constructs a CommandHandlers instance.
     *
     * @param eventSourcingHandler The event sourcing handler to use for saving the root aggregate.
     * @param properties
     */
    @Autowired
    public TenantCommandHandlers(EventSourcingHandler<AggregateRoot> eventSourcingHandler, KillBillClientProperties properties) {
        this.eventSourcingHandler = eventSourcingHandler;
        this.properties = properties;
    }


    @Override
    public void handle(CreateTenantCommand createTenantCommand)  {
        TenantApi tenantApi = new TenantApi(properties.getHttpClient());
        TenantAggregate tenantAggregate = new TenantAggregate(createTenantCommand);
        List<AuditLog> EMPTY_AUDIT_LOGS = List.of();
        Tenant body = new Tenant(createTenantCommand.getTenantId(),
                createTenantCommand.getExternalKey(),
                createTenantCommand.getApiKey(),
                createTenantCommand.getApiSecret(),
                EMPTY_AUDIT_LOGS);

        try {
            tenantApi.createTenant(body, properties.getRequestOptions());
        } catch (KillBillClientException e) {
            throw new RuntimeException(e);
        }
        eventSourcingHandler.save(tenantAggregate);

    }
}
