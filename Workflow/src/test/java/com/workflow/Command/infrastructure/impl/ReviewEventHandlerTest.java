package com.workflow.Command.infrastructure.impl;


import com.oga.cqrsref.producer.EventProducer;
import com.oga.workflow.command.aggregate.BookAggregate;
import com.oga.workflow.command.aggregate.ReviewAggregate;
import com.oga.workflow.command.commands.CreateReviewCommand;
import com.oga.workflow.command.infrastructure.EventSourcingHandler;
import com.oga.workflow.command.infrastructure.EventStore;
import com.oga.workflow.command.model.EventModel;
import com.oga.workflow.command.rest.entities.Review;
import com.oga.workflow.command.infrastructure.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class ReviewEventHandlerTest {

    @Mock
    private EventStore eventStore;

    @Mock
    private EventProducer eventProducer;

    private EventSourcingHandler<ReviewAggregate> reviewEventHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reviewEventHandler = new ReviewEventHandler(eventStore, eventProducer);
    }

    @Test
    void testSave() {
        // Arrange
        CreateReviewCommand createReviewCommand = CreateReviewCommand.builder()
                .commandId("123")
                .reviewer("John Doe")
                .content("Great book!")
                .published(true)
                .bookId(2)
                .build();
        ReviewAggregate reviewAggregate = new ReviewAggregate(createReviewCommand);
        reviewAggregate.setId("123");
        reviewAggregate.setReview(new Review());
        reviewAggregate.setBookId(456);

        ArgumentCaptor<EventModel> eventCaptor = ArgumentCaptor.forClass(EventModel.class);

        // Mock the behavior of eventStore.saveEvents() method
        doNothing().when(eventStore).saveEvents(eventCaptor.capture());

        // Act
        reviewEventHandler.save(reviewAggregate, "ReviewCreatedEvent");

        // Assert
        EventModel savedEvent = eventCaptor.getValue();
        assertEquals("123", savedEvent.getId());
        assertEquals("123", savedEvent.getAggregateIdentifier());
        assertEquals(BookAggregate.AGGREGATE_TYPE, savedEvent.getAggregateType());
        assertEquals("ReviewCreatedEvent", savedEvent.getEventType());
        assertEquals(reviewAggregate.getReview(), savedEvent.getEventData().get("review"));
        assertEquals("456", savedEvent.getEventData().get("book_id"));
        assertEquals(new Date().toString(), savedEvent.getTimeStamp().toString());

        verify(eventStore, times(1)).saveEvents(any(EventModel.class));
    }


}
