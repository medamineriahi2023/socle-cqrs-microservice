package com.oga.workflow.command.aggregate;

import com.oga.workflow.command.commands.CreateBookCommand;
import com.oga.workflow.command.rest.entities.Book;
import lombok.Data;

import java.util.UUID;

/**
 * The aggregate representing a Book.
 */
@Data
public class BookAggregate extends AggregateRoot {
    private String id;
    private Book book;
    public static final String AGGREGATE_TYPE = "BookAggregate";

    /**
     * Constructs a new BookAggregate instance based on the CreateBookCommand.
     *
     * @param createBookCommand The CreateBookCommand containing the book details.
     */
    public BookAggregate(CreateBookCommand createBookCommand) {
        // Create a new Book entity
        this.book = new Book();
        this.book.setTitle(createBookCommand.getTitle());
        this.book.setAuthor(createBookCommand.getAuthor());

        // Generate a unique identifier for the aggregate
        this.id = UUID.randomUUID().toString();
    }
}
