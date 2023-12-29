package com.workflow.Command.infrastructure.impl;


import com.oga.cqrsref.producer.EventProducer;
import com.oga.workflow.command.aggregate.BookAggregate;
import com.oga.workflow.command.infrastructure.EventSourcingHandler;
import com.oga.workflow.command.infrastructure.EventStore;
import com.oga.workflow.command.infrastructure.impl.BookEventHandler;
import com.oga.workflow.command.model.EventModel;
import com.oga.workflow.command.rest.entities.Book;
import com.oga.workflow.command.commands.CreateBookCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookEventHandlerTest {

    @Mock
    private EventStore eventStore;

    @Mock
    private EventProducer eventProducer;

    private EventSourcingHandler<BookAggregate> bookEventHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookEventHandler = new BookEventHandler(eventStore, eventProducer);
    }

    @Test
    void testSave() {

        // Arrange
        CreateBookCommand createBookCommand = CreateBookCommand.builder()
                .commandId("123")
                .title("Book Title")
                .author("Book Author")
                .build();
        BookAggregate bookAggregate = new BookAggregate(createBookCommand);
        bookAggregate.setId("123");
        bookAggregate.setBook(new Book());

        ArgumentCaptor<EventModel> eventCaptor = ArgumentCaptor.forClass(EventModel.class);

        // Mock the behavior of eventStore.saveEvents() method
        doNothing().when(eventStore).saveEvents(eventCaptor.capture());

        // Act
        bookEventHandler.save(bookAggregate, "BookCreatedEvent");

        // Assert
        EventModel savedEvent = eventCaptor.getValue();
        assertEquals("123", savedEvent.getId());
        assertEquals("123", savedEvent.getAggregateIdentifier());
        assertEquals(BookAggregate.AGGREGATE_TYPE, savedEvent.getAggregateType());
        assertEquals("BookCreatedEvent", savedEvent.getEventType());
        assertEquals(bookAggregate.getBook(), savedEvent.getEventData().get("book"));
        assertEquals(new Date().toString(), savedEvent.getTimeStamp().toString());

        verify(eventStore, times(1)).saveEvents(any(EventModel.class));
    }


}
