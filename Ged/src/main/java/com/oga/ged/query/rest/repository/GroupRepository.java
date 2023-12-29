package com.oga.ged.query.rest.repository;

import com.oga.ged.query.rest.dto.Group;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for managing Group entities.
 */
@Primary
public interface GroupRepository extends MongoRepository<Group,String> {
}
