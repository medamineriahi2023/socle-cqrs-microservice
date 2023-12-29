
package com.oga.workflow.command.events;

import com.oga.workflow.command.model.EventModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


/**
 * This repository provides access to the event store, which is a collection of
 *
 * all events that have occurred in the system. Events are used to track changes
 *
 * to the state of the system and are stored in a MongoDB database.
 *
 * This repository extends the {@link MongoRepository} interface and provides
 *
 * methods for querying events based on the identifier of the aggregate that
 *
 * generated them.
 */


@Qualifier
public interface EventStoreRepository extends MongoRepository<EventModel, String> {
    List<EventModel> findByAggregateIdentifier(String aggregateIdentifier);
}
