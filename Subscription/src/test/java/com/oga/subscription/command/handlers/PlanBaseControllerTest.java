package com.oga.subscription.command.handlers;

import com.oga.cqrsref.domain.AggregateRoot;
import com.oga.cqrsref.handlers.EventSourcingHandler;
import com.oga.cqrsref.infrastructure.CommandDispatcher;
import com.oga.subscription.command.commands.catalog.CreatePlanCommand;
import com.oga.subscription.command.commands.catalog.DeleteCatalogCommand;
import com.oga.subscription.command.config.KillBillClientProperties;
import com.oga.subscription.command.rest.constant.Constant;
import com.oga.subscription.command.rest.controller.PlanBaseController;
import com.oga.subscription.command.rest.response.BaseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.killbill.billing.catalog.api.BillingPeriod;
import org.killbill.billing.catalog.api.Currency;
import org.killbill.billing.catalog.api.ProductCategory;
import org.killbill.billing.catalog.api.TimeUnit;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PlanBaseControllerTest {

    private Logger logger = LoggerFactory.getLogger(PlanBaseControllerTest.class);

    private PlanCommandHandlers planCommandHandlers;
    private EventSourcingHandler<AggregateRoot> planEventSourcingHandler;
    private KillBillClientProperties properties;


    @Mock
    private CommandDispatcher commandDispatcher;

    @InjectMocks
    private PlanBaseController planBaseController;

    PlanBaseControllerTest() {

    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        planEventSourcingHandler = mock(EventSourcingHandler.class);
        properties = mock(KillBillClientProperties.class);
        planCommandHandlers = new PlanCommandHandlers(planEventSourcingHandler, properties);
    }

    @Test
    void handle_CreatePlanCommand() {
        // Arrange
        CreatePlanCommand createPlanCommand = new CreatePlanCommand();
        createPlanCommand.setPlanId(UUID.randomUUID().toString());
        createPlanCommand.setProductName("Gold Plan");
        createPlanCommand.setProductCategory(ProductCategory.BASE);
        createPlanCommand.setCurrency(Currency.USD);
        createPlanCommand.setAmount(new BigDecimal("9.99"));
        createPlanCommand.setBillingPeriod(BillingPeriod.MONTHLY);
        createPlanCommand.setTrialLength(30);
        createPlanCommand.setTrialTimeUnit(TimeUnit.DAYS);
        List<String> availableBaseProducts = Arrays.asList("Product1", "Product2");
        createPlanCommand.setAvailableBaseProducts(availableBaseProducts);

        // Act
        ResponseEntity<BaseResponse> response = planBaseController.createPlan(createPlanCommand);

        // Assert
        verify(commandDispatcher, times(1)).send(createPlanCommand);
        verify(commandDispatcher, times(1)).send(any(CreatePlanCommand.class)); // Additional verification example

        // Validate the response
        assert response.getStatusCode() == HttpStatus.CREATED;
        assert response.getBody() != null;
        assert response.getBody().getMessage().equals(Constant.SUCCESS_MESSAGE);

        // Indicate test success
        logger.info("handle_CreatePlanCommand test passed successfully.");
    }


    @Test
    void handle_DeleteCatalogCommand() {
        // Arrange
        DeleteCatalogCommand deleteCatalogCommand = new DeleteCatalogCommand();
        deleteCatalogCommand.setId(UUID.randomUUID().toString());

        // Act
        ResponseEntity<BaseResponse> response = planBaseController.deletePlan();

        // Assert
        verify(commandDispatcher, times(1)).send(deleteCatalogCommand);
        verify(commandDispatcher, times(1)).send(any(DeleteCatalogCommand.class)); // Additional verification example

        // Validate the response
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().getMessage().equals(Constant.SUCCESS_MESSAGE);

        // Indicate test success
        System.out.println("handle_DeleteCatalogCommand test passed successfully.");
    }

}
