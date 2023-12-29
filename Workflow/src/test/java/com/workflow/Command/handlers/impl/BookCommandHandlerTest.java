package com.workflow.Command.handlers.impl;
import com.oga.cqrsref.events.BaseEvent;
import com.oga.cqrsref.producer.EventProducer;
import com.oga.workflow.command.aggregate.BookAggregate;
import com.oga.workflow.command.commands.CreateBookCommand;
import com.oga.workflow.command.events.impl.BookCreatedEvent;
import com.oga.workflow.command.infrastructure.impl.BookEventHandler;
import com.oga.workflow.command.handlers.impl.*;
import com.oga.workflow.command.rest.entities.Book;
import com.oga.workflow.command.rest.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.eq; // Ajout de cette importation

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookCommandHandlerTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private EventProducer eventProducer;

    @Mock
    private BookEventHandler bookEventHandler;

    private BookCommandHandler bookCommandHandler;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        bookCommandHandler = new BookCommandHandler(bookRepository, eventProducer, bookEventHandler);
    }

    @Test
    public void testHandleCreateBookCommand() {
        // Arrange
        CreateBookCommand command = new CreateBookCommand("1","Title", "Author");

        Book book = new Book();
        book.setId(1);
        book.setTitle(command.getTitle());
        book.setAuthor(command.getAuthor());

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Act
        int bookId = bookCommandHandler.handle(command);

        // Assert
        assertEquals(1, bookId);
        verify(bookRepository).save(any(Book.class));
        verify(eventProducer).produce(eq(BookCreatedEvent.EVENT_TYPE), any(BaseEvent.class)); // Utilisation de eq() pour le premier argument
        verify(bookEventHandler).save(any(BookAggregate.class), eq(BookCreatedEvent.EVENT_TYPE)); // Utilisation de eq() pour le deuxième argument
    }
}
