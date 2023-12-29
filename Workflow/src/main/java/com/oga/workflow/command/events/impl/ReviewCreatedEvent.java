package com.oga.workflow.command.events.impl;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
 * Represents the event of a review being created.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class ReviewCreatedEvent extends BaseEvent {

    /**
     * The event type identifier.
     */
    public static final String EVENT_TYPE = "ReviewCreatedEvent";
    /**
     * The ID of the book for which the review was created.
     */
    private String bookId;

    /**
     * The reviewer of the book.
     */
    private String reviewer;

    /**
     * The content of the review.
     */
    private String content;

    /**
     * Indicates whether the review is published.
     */
    private boolean published;
}