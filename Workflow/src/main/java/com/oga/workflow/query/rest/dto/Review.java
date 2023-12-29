package com.oga.workflow.query.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "review")
public class Review {
    @MongoId
    private int idReview;
    private Book book;
    private String reviewer;
    private String content;
    private boolean published;
}
