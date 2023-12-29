package com.oga.ged.query.rest.repository;

import com.oga.ged.query.rest.dto.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for managing User entities.
 */
@Primary
public interface UserRepository extends MongoRepository<User,String> {
}
