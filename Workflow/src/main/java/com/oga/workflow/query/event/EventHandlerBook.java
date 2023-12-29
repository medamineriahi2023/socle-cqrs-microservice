package com.oga.workflow.query.event;

import com.oga.workflow.command.events.impl.BookCreatedEvent;

public interface EventHandlerBook {
    void on(BookCreatedEvent createBookEvent);

}
