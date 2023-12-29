package com.oga.workflow.query.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**

 This class represents a DTO (Data Transfer Object) for an employee.
 It extends the BaseEntity class and contains properties such as the employee's id, name, and last name.
 This class is annotated with Lombok's annotations: Data, NoArgsConstructor, AllArgsConstructor, and Builder, to generate boilerplate code.
 The Document annotation from Spring Data MongoDB is used to specify the name of the collection where this entity will be stored.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "book")
public class Book {
    @Id
    private String id;
    private String title;
    private String author;



}
