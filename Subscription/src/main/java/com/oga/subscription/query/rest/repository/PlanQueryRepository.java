package com.oga.subscription.query.rest.repository;

import com.oga.subscription.query.rest.dto.Plan;
import com.oga.subscription.query.rest.dto.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PlanQueryRepository extends MongoRepository<Plan,Integer> {
    Optional<Product> findById(String id);

}
