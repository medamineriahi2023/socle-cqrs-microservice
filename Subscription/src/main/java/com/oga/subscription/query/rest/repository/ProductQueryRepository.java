package com.oga.subscription.query.rest.repository;

import com.oga.subscription.query.rest.dto.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductQueryRepository extends MongoRepository<Product,Integer> {
    Optional<Product> findById(String id);

}
