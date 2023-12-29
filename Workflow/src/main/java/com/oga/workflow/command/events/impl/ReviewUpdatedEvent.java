package com.oga.workflow.command.events.impl;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents the event of a review being updated.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class ReviewUpdatedEvent extends BaseEvent {

    /**
     * The event type identifier.
     */
    public static final String EVENT_TYPE = "ReviewUpdatedEvent";

    /**
     * The ID of the review being updated.
     */
    private int reviewId;

    /**
     * The updated content of the review.
     */
    private String content;

    /**
     * Indicates whether the review is published.
     */
    private boolean published;
}

