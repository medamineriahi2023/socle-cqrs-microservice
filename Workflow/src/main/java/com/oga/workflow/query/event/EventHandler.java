package com.oga.workflow.query.event;

import com.oga.workflow.command.model.EventModel;
import org.springframework.stereotype.Service;

@Service
public interface EventHandler {

    /**
     * Handles the given create Book event.
     *
     * @param event the create Book event to handle
     */
    void on(EventModel event);
}
