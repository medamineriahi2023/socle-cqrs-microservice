package com.oga.workflow.command.events.impl;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * Represents the event of a book being created.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class BookCreatedEvent extends BaseEvent {
    /**
     * The event type identifier.
     */
    public static final String EVENT_TYPE = "BookCreatedEvent";
    /**
     * The ID of the created book.
     */
    private String bookId;

    /**
     * The title of the created book.
     */
    private String title;

    /**
     * The author of the created book.
     */
    private String author;


}

