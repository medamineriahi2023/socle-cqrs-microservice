package com.oga.workflow.query.event;

import com.oga.workflow.command.events.impl.ReviewCreatedEvent;
import com.oga.workflow.command.events.impl.ReviewUpdatedEvent;

public interface EventHandlerReview {

    void on (ReviewCreatedEvent createReviewEvent);
    void on (ReviewUpdatedEvent updateReviewEvent);
}
