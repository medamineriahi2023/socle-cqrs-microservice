package com.oga.workflow.command.commands;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents a command to create a review for a book.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class CreateReviewCommand extends BaseCommand {

    /**
     * The ID of the command. It uniquely identifies the command.
     */
    private String commandId;

    /**
     * The ID of the book for which the review is being created.
     */
    private int bookId;

    /**
     * The reviewer's name.
     */
    private String reviewer;

    /**
     * The content of the review.
     */
    private String content;

    /**
     * Indicates whether the review is published or not.
     */
    private boolean published;



}
