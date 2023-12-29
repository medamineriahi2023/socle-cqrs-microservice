package com.oga.ged.query.rest.repository;

import com.oga.ged.query.rest.dto.PersonGroupAssignment;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing PersonGroupAssignment entities.
 */
@Primary
public interface PersonGroupAssignmentRepository extends MongoRepository<PersonGroupAssignment,String> {
    Optional<PersonGroupAssignment> findByGroupIdAndPersonId(String groupId, String personId);
    @Query(value = "{'groupId': ?0}")
    List<PersonGroupAssignment> findByGroupId(String groupId);
    @Query(value = "{'personId': ?0}")
    List<PersonGroupAssignment> findByPersonId(String personId);
}
