package com.oga.workflow.query.event.impl; /**
 * The CreateReviewEvent class is an implementation of the EventHandler interface
 * that handles the creation of a review event by saving it to the database.
 *//*

package com.workflow.query.event.impl;

import com.oga.cqrsref.events.BaseEvent;
import com.oga.workflow.command.model.EventModel;
import com.workflow.query.event.EventHandler;
import com.workflow.query.rest.dto.Book;
import com.workflow.query.rest.dto.Review;
import com.workflow.query.rest.repository.ReviewEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@EnableMongoRepositories("com.workflow.query.rest.repository")
public class CreateReviewEvent implements EventHandler {

    @Autowired
    ReviewEventRepository eventRepository;

    */
/**
     * Handles the review creation event by extracting the review data from the event,
     * creating a new Review object, and saving it to the database.
     *
     * @param event The event model representing the review creation event.
     *//*

    @Override
    public void on(EventModel event) {
        // Extract the review data from the event
        Map<String, Object> eventData = event.getEventData();
        Map<String, Object> reviewData = (Map<String, Object>) eventData.get("review");

        // Create a new Review object
        Review review = new Review();

        // Extract the book data from the review data
        Map<String, Object> bookData = (Map<String, Object>) reviewData.get("book");
        Book book = new Book();
        book.setId(bookData.get("id").toString());
        book.setTitle((String) bookData.get("title"));
        book.setAuthor((String) bookData.get("author"));
        review.setBook(book);

        review.setIdReview((int) reviewData.get("id"));
        review.setReviewer((String) reviewData.get("reviewer"));
        review.setContent((String) reviewData.get("content"));
        review.setPublished((Boolean) reviewData.get("published"));

        // Save the review to the database
        eventRepository.save(review);
    }
}
*/
