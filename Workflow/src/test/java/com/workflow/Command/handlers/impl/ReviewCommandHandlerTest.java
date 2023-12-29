package com.workflow.Command.handlers.impl;

import com.oga.cqrsref.producer.EventProducer;
import com.oga.workflow.command.aggregate.ReviewAggregate;
import com.oga.workflow.command.commands.CreateReviewCommand;
import com.oga.workflow.command.commands.UpdateReviewCommand;
import com.oga.workflow.command.events.impl.ReviewCreatedEvent;
import com.oga.workflow.command.events.impl.ReviewUpdatedEvent;
import com.oga.workflow.command.handlers.impl.ReviewCommandHandler;
import com.oga.workflow.command.infrastructure.impl.ReviewEventHandler;
import com.oga.workflow.command.rest.entities.Book;
import com.oga.workflow.command.rest.entities.Review;
import com.oga.workflow.command.rest.repository.ReviewRepository;
import com.oga.workflow.command.rest.services.impl.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReviewCommandHandlerTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private EventProducer eventProducer;

    @Mock
    private ReviewEventHandler reviewEventHandler;

    @Mock
    private BookService bookService;

    private ReviewCommandHandler reviewCommandHandler;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        reviewCommandHandler = new ReviewCommandHandler(reviewRepository, eventProducer, reviewEventHandler, bookService);
    }

    @Test
    public void testHandleCreateReviewCommand() {
        // Arrange
        CreateReviewCommand command = new CreateReviewCommand("1",1,"Reviewer", "Content" , true);
        Review review = new Review();
        review.setId(1);
        review.setReviewer(command.getReviewer());
        review.setContent(command.getContent());
        review.setPublished(command.isPublished());
        Book book = new Book();
        book.setId(command.getBookId());
        when(bookService.getBookById(command.getBookId())).thenReturn(book);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        // Act
        int reviewId = reviewCommandHandler.handle(command);

        // Assert
        assertEquals(1, reviewId);
        verify(bookService).getBookById(command.getBookId());
        verify(reviewRepository).save(any(Review.class));
        verify(reviewEventHandler).save(any(ReviewAggregate.class), any(String.class));
        verify(eventProducer).produce(eq(ReviewCreatedEvent.EVENT_TYPE), any());
    }

    @Test
    public void testHandleUpdateReviewCommand() {
        // Arrange
        UpdateReviewCommand command = new UpdateReviewCommand();
        command.setReviewId(1);
        command.setCommandId("1");
        command.setPublished(true);
        Book book=new Book();

        Review review = new Review();
        review.setId(1);
        review.setBook(book);
        review.setContent("Old content");
        review.setPublished(true);
        Optional<Review> reviewOptional = Optional.of(review);
        when(reviewRepository.findById(command.getReviewId())).thenReturn(reviewOptional);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        // Act
        reviewCommandHandler.handle(command);

        // Assert
        verify(reviewRepository).findById(command.getReviewId());
        verify(reviewRepository).save(any(Review.class));
        verify(reviewEventHandler).save(any(ReviewAggregate.class), any(String.class));
        verify(eventProducer).produce(eq(ReviewUpdatedEvent.EVENT_TYPE), any());
    }
}

