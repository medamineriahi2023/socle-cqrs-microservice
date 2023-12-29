package com.oga.subscription.query.rest.dto;

import com.oga.cqrsref.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.killbill.billing.catalog.api.*;
import org.killbill.billing.client.model.gen.EventSubscription;
import org.killbill.billing.client.model.gen.PhasePrice;
import org.killbill.billing.entitlement.api.Entitlement;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**

 This class represents a DTO (Data Transfer Object) .
 It extends the BaseEntity class and contains properties such as the product's id, name, and last name.
 This class is annotated with Lombok's annotations: Data, NoArgsConstructor, AllArgsConstructor, and Builder, to generate boilerplate code.
 The Document annotation from Spring Data MongoDB is used to specify the name of the collection where this entity will be stored.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "subscription")
public class Subscription extends BaseEntity {
    @Id
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
