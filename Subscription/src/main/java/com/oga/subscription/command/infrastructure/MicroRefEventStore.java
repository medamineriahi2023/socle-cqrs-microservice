
package com.oga.subscription.command.infrastructure;

import com.oga.cqrsref.infrastructure.EventStore;
import com.oga.cqrsref.producer.EventProducer;
import com.oga.subscription.command.events.EventStoreRepository;
import com.oga.subscription.command.rest.constant.Constant;
import com.oga.cqrsref.domain.AggregateRoot;
import com.oga.cqrsref.events.BaseEvent;
import com.oga.cqrsref.events.EventModel;
import com.oga.cqrsref.exceptions.AggregateNotFoundException;
import com.oga.cqrsref.exceptions.ConcurrencyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
     This class that provides implementation of the EventStore interface for storing events.
 */

@Service
@EnableMongoRepositories("com.oga.subscription.command.events")
@ComponentScan("com.oga.cqrsref.producer")
public class MicroRefEventStore implements EventStore {
    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private EventStoreRepository eventStoreRepository;


/**
     Saves the events of a specific aggregate to the event store.
     @param aggregateId the ID of the aggregate to save events for
     @param events the events to be saved
     @param expectedVersion the expected version of the aggregate
     @throws ConcurrencyException if the expected version does not match the actual version in the event store
     */

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }
        var version = expectedVersion;
        for (var event: events) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .id(aggregateId)
                    .timeStamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AggregateRoot.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();


            var persistedEvent = eventStoreRepository.save(eventModel);
            if (persistedEvent.getId()!=null) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }


/**
     Retrieves all events for a specific aggregate.
     @param aggregateId the ID of the aggregate to retrieve events for
     @return a list of all events for the given aggregate ID
     @throws AggregateNotFoundException if the provided aggregate ID is not found in the event store
     */

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException("Incorrect account ID provided!");
        }
        return eventStream.stream().map(x -> x.getEventData()).collect(Collectors.toList());
    }

/**
     Retrieves the IDs of all aggregates stored in the event store.
     @return a list of all aggregate IDs
     @throws IllegalStateException if the event store could not be accessed
     */

    @Override
    public List<String> getAggregateIds() {
        var eventStream = eventStoreRepository.findAll();
        if (eventStream == null || eventStream.isEmpty()) {
            throw new IllegalStateException(Constant.COULD_NOT_RETRIEVE_EVENT_STREAM);
        }
        return eventStream.stream().map(EventModel::getAggregateIdentifier).distinct().collect(Collectors.toList());
    }

}
