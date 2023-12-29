package com.oga.subscription.command.handlers;

import com.oga.cqrsref.domain.AggregateRoot;
import com.oga.cqrsref.handlers.EventSourcingHandler;
import com.oga.subscription.command.commands.product.CreateProductCommand;
import com.oga.subscription.command.commands.product.DeleteProductCommand;
import com.oga.subscription.command.commands.product.UpdateProductCommand;
import com.oga.subscription.command.domain.ProductAggregate;
import com.oga.subscription.command.rest.dto.Product;
import com.oga.subscription.command.rest.repository.JpaRepos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.killbill.billing.catalog.api.ProductCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    private Logger logger = LoggerFactory.getLogger(ProductControllerTest.class);

    private ProductCommandHandlers productCommandHandlers;
    private JpaRepos jpaRepos;
    private EventSourcingHandler<AggregateRoot> productEventSourcingHandler;

    @BeforeEach
    void setUp() {
        jpaRepos = mock(JpaRepos.class);
        productEventSourcingHandler = mock(EventSourcingHandler.class);
        productCommandHandlers = new ProductCommandHandlers(productEventSourcingHandler,  jpaRepos);
    }

    @Test
    void handle_CreateProductCommand() {
        // Arrange
        CreateProductCommand createProductCommand = new CreateProductCommand("music", ProductCategory.BASE, "Spotify");

        // Act
        productCommandHandlers.handle(createProductCommand);

        // Assert
        verify(jpaRepos, times(1)).save(any(Product.class));
        verify(productEventSourcingHandler, times(1)).save(any(ProductAggregate.class));

        // Indicate test success
        logger.info("handle_CreateProductCommand_Successful test passed successfully.");
    }
    @Test
    void handle_UpdateProductCommand() {
        // Arrange
        UpdateProductCommand updateProductCommand = new UpdateProductCommand("entertainment", ProductCategory.BASE, "Spotify");
        updateProductCommand.setId(UUID.randomUUID().toString());
        // Act
        Product product = new Product(updateProductCommand.getId(),"music",ProductCategory.BASE,"Spotify");
        when(jpaRepos.findById(updateProductCommand.getId())).thenReturn(Optional.of(product));
        productCommandHandlers.handle(updateProductCommand);

        // Assert
        verify(jpaRepos, times(1)).save(any(Product.class));
        verify(productEventSourcingHandler, times(1)).save(any(ProductAggregate.class));

        // Indicate test success
        logger.info("handle_CreateProductCommand_Successful test passed successfully.");
    }  @Test
    void handle_DeleteProductCommand() {
        // Arrange
        DeleteProductCommand deleteProductCommand = new DeleteProductCommand();
        deleteProductCommand.setId(UUID.randomUUID().toString());
        // Act
        Product product = new Product(deleteProductCommand.getId(),"music",ProductCategory.BASE,"Spotify");
        when(jpaRepos.findById(deleteProductCommand.getId())).thenReturn(Optional.of(product));
        productCommandHandlers.handle(deleteProductCommand);

        // Assert
        verify(jpaRepos, times(1)).delete(any(Product.class));
        verify(productEventSourcingHandler, times(1)).save(any(ProductAggregate.class));

        // Indicate test success
        logger.info("handle_CreateProductCommand_Successful test passed successfully.");
    }


}
