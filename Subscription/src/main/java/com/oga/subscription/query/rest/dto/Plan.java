package com.oga.subscription.query.rest.dto;

import com.oga.cqrsref.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.killbill.billing.catalog.api.BillingPeriod;
import org.killbill.billing.catalog.api.Currency;
import org.killbill.billing.catalog.api.ProductCategory;
import org.killbill.billing.catalog.api.TimeUnit;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

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
@Document(collection = "plan")
public class Plan extends BaseEntity {
    @Id
    private String id;
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
