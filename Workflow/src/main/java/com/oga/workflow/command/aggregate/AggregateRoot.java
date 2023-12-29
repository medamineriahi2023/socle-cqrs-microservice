package com.oga.workflow.command.aggregate;

import com.oga.workflow.command.model.EventModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The abstract class representing an aggregate root.
 * Aggregate roots are entities that define consistency and transactional boundaries within the domain model.
 * They handle domain events and maintain a list of uncommitted events.
 */
public abstract class AggregateRoot {
    private List<EventModel> uncommittedEvents = new ArrayList<>();

    /**
     * Gets the list of uncommitted events.
     *
     * @return The list of uncommitted events.
     */
    public List<EventModel> getUncommittedEvents() {
        return uncommittedEvents;
    }

    /**
     * Marks the changes as committed by clearing the list of uncommitted events.
     */
    public void markChangesAsCommitted() {
        uncommittedEvents.clear();
    }

    /**
     * Adds an event to the list of uncommitted events.
     *
     * @param event The event to add.
     */
    protected void addEvent(EventModel event) {
        uncommittedEvents.add(event);
    }

    /**
     * Applies an event to the aggregate root to update its state.
     * This method should be implemented by concrete aggregate classes.
     *
     * @param event The event to apply.
     */
    protected  void apply(EventModel event){

    }

    /**
     * Replays a list of events on the aggregate root to restore its state.
     *
     * @param events The list of events to replay.
     */
    public void replayEvents(List<EventModel> events) {
        for (EventModel event : events) {
            apply(event);
        }
    }
}
