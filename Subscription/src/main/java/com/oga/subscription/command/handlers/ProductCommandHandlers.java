package com.oga.subscription.command.handlers;

import com.oga.subscription.command.commands.product.CreateProductCommand;
import com.oga.subscription.command.commands.product.DeleteProductCommand;
import com.oga.subscription.command.commands.product.UpdateProductCommand;
import com.oga.subscription.command.domain.ProductAggregate;
import com.oga.subscription.command.rest.dto.Product;
import com.oga.subscription.command.rest.repository.JpaRepos;
import com.oga.cqrsref.domain.AggregateRoot;
import com.oga.cqrsref.handlers.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;


/**
    This class represents the implementation of the CommandHandlerInterface that handles
    incoming commands .
 */

@Service
@EnableJpaRepositories("com.oga.subscription.command.rest.repository")
@ComponentScan("com.oga.cqrsref.handlers")
public class ProductCommandHandlers implements ProductCommandHandlerInterface {

    private final EventSourcingHandler<AggregateRoot>  eventSourcingHandler;
    private final JpaRepos jpaRepos;




    /**
     * Constructs a CommandHandlers instance.
     *
     * @param eventSourcingHandler The event sourcing handler to use for saving the root aggregate.
     * @param jpaRepos The JPA repository for accessing and modifying product data.
     */
    @Autowired
    public ProductCommandHandlers(EventSourcingHandler<AggregateRoot> eventSourcingHandler,
                                  JpaRepos jpaRepos) {
        this.eventSourcingHandler = eventSourcingHandler;
        this.jpaRepos = jpaRepos;
    }

    @Override
    public void handle(CreateProductCommand createProductCommand) {
        ProductAggregate productAggregate = new ProductAggregate(createProductCommand);
        Product product =new Product(createProductCommand.getId(),createProductCommand.getName(),createProductCommand.getCategory(), createProductCommand.getType());
        jpaRepos.save(product);
        eventSourcingHandler.save(productAggregate);
    }

    @Override
    public void handle(UpdateProductCommand updateProductCommand) {
        ProductAggregate productAggregate = new ProductAggregate(updateProductCommand);
        jpaRepos.findById(updateProductCommand.getId()).ifPresent(product1-> {
            product1.setCategory(updateProductCommand.getCategory());
            product1.setName(updateProductCommand.getName());
            product1.setType(updateProductCommand.getType());
            jpaRepos.save(product1);
        });

        eventSourcingHandler.save(productAggregate);
    }

    @Override
    public void handle(DeleteProductCommand deleteProductCommand) {
      ProductAggregate productAggregate = new ProductAggregate(deleteProductCommand);
      jpaRepos.findById(deleteProductCommand.getId()).ifPresent(jpaRepos::delete);
        eventSourcingHandler.save(productAggregate);
    }


}
