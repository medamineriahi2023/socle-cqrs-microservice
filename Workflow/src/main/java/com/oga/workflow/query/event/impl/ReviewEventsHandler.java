package com.oga.workflow.query.event.impl;

import com.oga.workflow.command.events.impl.ReviewCreatedEvent;
import com.oga.workflow.command.events.impl.ReviewUpdatedEvent;
import com.oga.workflow.command.rest.entities.Book;
import com.oga.workflow.command.rest.entities.Review;
import com.oga.workflow.command.rest.repository.BookRepository;
import com.oga.workflow.command.rest.repository.ReviewRepository;
import com.oga.workflow.query.event.EventHandlerReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@EnableMongoRepositories("com.workflow.query.rest.repository")
public class ReviewEventsHandler implements EventHandlerReview {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookRepository bookRepository;


    @Override
    public void on(ReviewCreatedEvent createReviewEvent) {

        Optional<Book> book = bookRepository.findById(Integer.parseInt(createReviewEvent.getBookId()));
        Review review = new Review(createReviewEvent.getVersion(), book.get(), createReviewEvent.getReviewer(), createReviewEvent.getContent(), false);

        reviewRepository.save(review);
    }

    @Override
    public void on(ReviewUpdatedEvent updateReviewEvent) {
        Optional<Review> review = reviewRepository.findById(updateReviewEvent.getReviewId());

        if (review.isPresent()) {
            review.get().setContent(updateReviewEvent.getContent());
            review.get().setPublished(updateReviewEvent.isPublished());
            review.get().setId(updateReviewEvent.getReviewId());

            reviewRepository.save(review.get());
        } else {
            //
        }
    }
}
