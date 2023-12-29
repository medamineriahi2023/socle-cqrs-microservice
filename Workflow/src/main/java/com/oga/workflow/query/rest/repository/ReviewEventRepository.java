package com.oga.workflow.query.rest.repository;

import com.oga.workflow.query.rest.dto.Review;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


@Primary
public interface ReviewEventRepository extends MongoRepository<Review,String> {
    // Add custom query methods if needed
    Optional<Review> findByIdReview(int idReview);

}
