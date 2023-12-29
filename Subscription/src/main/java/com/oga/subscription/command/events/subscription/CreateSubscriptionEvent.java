package com.oga.subscription.command.events.subscription;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.killbill.billing.catalog.api.BillingPeriod;
import org.killbill.billing.catalog.api.PhaseType;
import org.killbill.billing.catalog.api.ProductCategory;
import org.killbill.billing.client.model.gen.EventSubscription;
import org.killbill.billing.client.model.gen.PhasePrice;
import org.killbill.billing.entitlement.api.Entitlement;

import java.util.List;
import java.util.UUID;

/**
 * Represents an event indicating the creation of a product.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class CreateSubscriptionEvent extends BaseEvent {
    private String identifier;
    private String id;
    private UUID accountId;
    private UUID bundleId;
    private String bundleExternalKey;
    private UUID subscriptionId;
    private String externalKey;
    private DateTime startDate;
    private String productName;
    private ProductCategory productCategory;
    private BillingPeriod billingPeriod;
    private PhaseType phaseType;
    private String priceList;
    private String planName;
    private Entitlement.EntitlementState state;
    private Entitlement.EntitlementSourceType sourceType;
    private DateTime cancelledDate;
    private LocalDate chargedThroughDate;
    private DateTime billingStartDate;
    private DateTime billingEndDate;
    private Integer billCycleDayLocal;
    private Integer quantity;
    private List<EventSubscription> events;
    private List<PhasePrice> priceOverrides;
    private List<PhasePrice> prices;

}
