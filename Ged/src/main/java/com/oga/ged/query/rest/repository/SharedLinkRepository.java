package com.oga.ged.query.rest.repository;

import com.oga.ged.query.rest.dto.SharedLink;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for managing SharedLink entities.
 */
@Primary
public interface SharedLinkRepository extends MongoRepository<SharedLink,String> {
}
