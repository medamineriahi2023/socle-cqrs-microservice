package com.oga.subscription.command.rest.dto;

import com.oga.cqrsref.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.killbill.billing.catalog.api.ProductCategory;


import javax.persistence.*;

/**
 * Represents a product entity.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="product")
public class Product extends BaseEntity {
    @Id
    private String id;
    private String type;
    private ProductCategory category;

    private String name;

}
