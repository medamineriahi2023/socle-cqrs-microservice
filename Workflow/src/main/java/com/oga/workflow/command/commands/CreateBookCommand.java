package com.oga.workflow.command.commands;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents a command to create a book.
 */
@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@JsonSerialize
public class CreateBookCommand extends BaseCommand {

    /**
     * The ID of the command. It uniquely identifies the command.
     */
    private String commandId;

    /**
     * The title of the book.
     */
    private String title;

    /**
     * The author of the book.
     */
    private String author;


}
