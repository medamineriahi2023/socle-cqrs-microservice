package com.oga.subscription.command.infrastructure;

import com.oga.cqrsref.domain.AggregateRoot;
import com.oga.cqrsref.handlers.EventSourcingHandler;
import com.oga.cqrsref.infrastructure.EventStore;
import com.oga.cqrsref.producer.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;


/**
 * Handles event sourcing for the ProductAggregate class.
 */

@Service
public class MicroRefEventSourcingHandler implements EventSourcingHandler {
    @Autowired
    private EventStore eventStore;

    @Autowired
    private EventProducer eventProducer;



/**
     * Saves the uncommitted changes of the given aggregate root to the event store.
     *
     * @param aggregateRoot the aggregate root to save
     */

    @Override
    public void save(AggregateRoot aggregateRoot) {
        eventStore.saveEvents(aggregateRoot.getId(), aggregateRoot.getUncommittedChanges(), aggregateRoot.getVersion());
        aggregateRoot.markChangesAsCommitted();
    }

/**
     * Retrieves the Product aggregate with the given id from the event store.
     *
     * @param id the id of the Product aggregate to retrieve
     * @return the Product aggregate with the given id
     */

    @Override
    public AggregateRoot getById(String id) {
        var aggregate = new AggregateRoot();
        var events = eventStore.getEvents(id);
        if (events != null && !events.isEmpty()) {
            aggregate.replayEvents(events);
            var latestVersion = events.stream().map(x -> x.getVersion()).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }
        return aggregate;
    }

/**
     * Republishes all events from the event store to their respective topics.
     */

    @Override
    public void republishEvents() {
        var aggregateIds = eventStore.getAggregateIds();
        for(var aggregateId: aggregateIds) {
            var aggregate = getById(aggregateId);
            if (aggregate == null ) continue;
            var events = eventStore.getEvents(aggregateId);
            for(var event: events) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }
}

