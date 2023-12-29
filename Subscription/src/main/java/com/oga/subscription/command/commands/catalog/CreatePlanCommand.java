package com.oga.subscription.command.commands.catalog;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.killbill.billing.catalog.api.BillingPeriod;
import org.killbill.billing.catalog.api.Currency;
import org.killbill.billing.catalog.api.ProductCategory;
import org.killbill.billing.catalog.api.TimeUnit;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents a command to create a plan.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePlanCommand extends BaseCommand {
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
