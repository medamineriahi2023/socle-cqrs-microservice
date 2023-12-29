package com.oga.subscription.query.rest.dto;

import com.oga.cqrsref.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.killbill.billing.catalog.api.ProductCategory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "product")
public class Product extends BaseEntity {
    @Id
    private String id;
    private ProductCategory category;
    private String type;
    private String name;


    public Product(String type, ProductCategory category, String name) {
        this.type = type;
        this.category=category;
        this.name = name;
    }  public Product(String id , String type, ProductCategory category, String name) {
        this.id = id;
        this.type = type;
        this.category=category;
        this.name = name;
    }
}
