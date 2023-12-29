package com.oga.workflow.query.event.impl; /**
 * The UpdateReviewEvent class is an implementation of the EventHandler interface
 * that handles the update of a review event by updating it in the database.
 *//*

package com.workflow.query.event.impl;

import com.oga.workflow.command.model.EventModel;
import com.workflow.query.event.EventHandler;
import com.workflow.query.rest.dto.Review;
import com.workflow.query.rest.repository.ReviewEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@EnableMongoRepositories("com.workflow.query.rest.repository")
public class UpdateReviewEvent implements EventHandler {

    @Autowired
    ReviewEventRepository eventRepository;

    */
/**
     * Handles the review update event by extracting the updated review data from the event,
     * finding the existing review in the database, updating its properties, and saving it.
     *
     * @param event The event model representing the review update event.
     *//*

    @Override
    public void on(EventModel event) {
        // Extract the review data from the event
        Map<String, Object> eventData = event.getEventData();
        Map<String, Object> reviewData = (Map<String, Object>) eventData.get("review");
        int reviewId = (int) reviewData.get("id");

        // Find the review in the database based on the reviewId
        Optional<Review> reviewOpt = eventRepository.findByIdReview(reviewId);
        if (reviewOpt.isPresent()) {
            Review review = reviewOpt.get();

            // Update the review properties with the updated data
            review.setContent((String) reviewData.get("content"));
            review.setPublished((Boolean) reviewData.get("published"));

            // Save the updated review to the database
            eventRepository.save(review);
        } else {
            // Handle the case when the review with the specified reviewId is not found
        }
    }
}
*/
