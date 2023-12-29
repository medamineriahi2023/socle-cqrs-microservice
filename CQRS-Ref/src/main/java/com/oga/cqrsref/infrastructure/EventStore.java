package com.oga.cqrsref.infrastructure;


import com.oga.cqrsref.events.BaseEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EventStore {
    void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion);
    List<BaseEvent> getEvents(String aggregateId);
    List<String> getAggregateIds();
}
