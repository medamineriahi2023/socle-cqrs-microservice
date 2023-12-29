package com.oga.subscription;

import com.oga.cqrsref.infrastructure.CommandDispatcher;
import com.oga.cqrsref.infrastructure.QueryDispatcher;
import com.oga.subscription.command.commands.catalog.CreatePlanCommand;
import com.oga.subscription.command.commands.catalog.DeleteCatalogCommand;
import com.oga.subscription.command.commands.product.CreateProductCommand;
import com.oga.subscription.command.commands.product.DeleteProductCommand;
import com.oga.subscription.command.commands.product.UpdateProductCommand;
import com.oga.subscription.command.commands.subscription.CreateSubscriptionCommand;
import com.oga.subscription.command.commands.tenant.CreateTenantCommand;
import com.oga.subscription.command.handlers.PlanCommandHandlers;
import com.oga.subscription.command.handlers.ProductCommandHandlers;
import com.oga.subscription.command.handlers.SubscriptionCommandHandlers;
import com.oga.subscription.command.handlers.TenantCommandHandlers;
import com.oga.subscription.query.handlers.PlanQueryHandler;
import com.oga.subscription.query.handlers.ProductQueryHandler;
import com.oga.subscription.query.handlers.SubscriptionQueryHandler;
import com.oga.subscription.query.queries.FindAllPlans;
import com.oga.subscription.query.queries.FindAllProducts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableKafka
@ComponentScan("com.oga.cqrsref.infrastructure")
@ComponentScan("com.oga.subscription.command.handlers")
@ComponentScan("com.oga.subscription.query.handlers")
public class SubscriptionApplication {

    private final ProductCommandHandlers productPlanCommandHandler;
    private final PlanCommandHandlers planCommandHandlers;
    private final SubscriptionCommandHandlers subscriptionCommandHandlers;
    private final TenantCommandHandlers tenantCommandHandlers;
    private final ProductQueryHandler productQueryHandler;
    private final PlanQueryHandler planQueryHandler;

    private final SubscriptionQueryHandler subscriptionQueryHandler;

    @Autowired
    private CommandDispatcher commandDispatcher;
    @Autowired
    private QueryDispatcher queryDispatcher;


    @Autowired
    public SubscriptionApplication(ProductCommandHandlers productPlanCommandHandler, PlanCommandHandlers planCommandHandlers, SubscriptionCommandHandlers subscriptionCommandHandlers, TenantCommandHandlers tenantCommandHandlers, ProductQueryHandler productQueryHandler, PlanQueryHandler planQueryHandler, SubscriptionQueryHandler subscriptionQueryHandler) {
        this.productPlanCommandHandler = productPlanCommandHandler;
        this.planCommandHandlers = planCommandHandlers;
        this.subscriptionCommandHandlers = subscriptionCommandHandlers;
        this.tenantCommandHandlers = tenantCommandHandlers;
        this.productQueryHandler = productQueryHandler;
        this.planQueryHandler = planQueryHandler;
        this.subscriptionQueryHandler = subscriptionQueryHandler;
    }


    @PostConstruct
    public void registerHandlers() {
        commandDispatcher.registerHandler(CreateProductCommand.class, productPlanCommandHandler::handle);
        commandDispatcher.registerHandler(UpdateProductCommand.class, productPlanCommandHandler::handle);
        commandDispatcher.registerHandler(DeleteProductCommand.class, productPlanCommandHandler::handle);
        commandDispatcher.registerHandler(CreatePlanCommand.class, planCommandHandlers::handle);
        commandDispatcher.registerHandler(DeleteCatalogCommand.class, planCommandHandlers::handle);
        commandDispatcher.registerHandler(CreateTenantCommand.class, tenantCommandHandlers::handle);
        commandDispatcher.registerHandler(CreateSubscriptionCommand.class, subscriptionCommandHandlers::handle);
        queryDispatcher.registerHandler(FindAllProducts.class, productQueryHandler::handle);
            queryDispatcher.registerHandler(FindAllPlans.class, planQueryHandler::handle);
    }

    public static void main(String[] args) {
        SpringApplication.run(SubscriptionApplication.class, args);
    }
}