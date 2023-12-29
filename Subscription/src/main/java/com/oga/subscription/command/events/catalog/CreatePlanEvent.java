package com.oga.subscription.command.events.catalog;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.killbill.billing.catalog.api.BillingPeriod;
import org.killbill.billing.catalog.api.Currency;
import org.killbill.billing.catalog.api.ProductCategory;
import org.killbill.billing.catalog.api.TimeUnit;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents an event indicating the creation of a product.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class CreatePlanEvent extends BaseEvent {
    private String identifier;
    private String planId ;
    private String productName ;
    private ProductCategory productCategory ;
    private Currency currency ;
    private BigDecimal amount ;
    private BillingPeriod billingPeriod ;
    private Integer trialLength ;
    private TimeUnit trialTimeUnit ;
    private List<String> availableBaseProducts ;
}
