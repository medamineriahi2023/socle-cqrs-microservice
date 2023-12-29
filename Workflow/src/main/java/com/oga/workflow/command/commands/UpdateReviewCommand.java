package com.oga.workflow.command.commands;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents a command to update a review.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UpdateReviewCommand extends BaseCommand {

    /**
     * The ID of the command. It uniquely identifies the command.
     */
    private String commandId;

    /**
     * The ID of the review to be updated.
     */
    private int reviewId;

    /**
     * The updated content of the review.
     */
    private String content;

    /**
     * Indicates whether the review is published or not.
     */
    private boolean published;



}
