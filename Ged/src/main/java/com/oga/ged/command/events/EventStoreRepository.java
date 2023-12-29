package com.oga.ged.command.events;

import com.oga.cqrsref.events.EventModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


/**
 * Repository for storing and retrieving event models.
 */
@Qualifier
public interface EventStoreRepository extends MongoRepository<EventModel, String> {
    List<EventModel> findByAggregateIdentifier(String aggregateIdentifier);
}
