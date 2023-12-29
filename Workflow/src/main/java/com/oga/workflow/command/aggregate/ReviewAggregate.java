package com.oga.workflow.command.aggregate;

import com.oga.workflow.command.commands.CreateReviewCommand;
import com.oga.workflow.command.commands.UpdateReviewCommand;
import com.oga.workflow.command.rest.entities.Book;
import com.oga.workflow.command.rest.entities.Review;
import lombok.Data;

import java.util.UUID;

/**
 * The aggregate representing a Review.
 */
@Data
public class ReviewAggregate extends AggregateRoot {
    private String id;
    private Review review;
    private int bookId;
    public static final String AGGREGATE_TYPE = "BookAggregate";

    /**
     * Constructs a new ReviewAggregate instance based on the CreateReviewCommand.
     *
     * @param createReviewCommand The CreateReviewCommand containing the review details.
     */
    public ReviewAggregate(CreateReviewCommand createReviewCommand) {
        this.review = new Review();

        this.review.setReviewer(createReviewCommand.getReviewer());
        this.review.setContent(createReviewCommand.getContent());
        this.review.setPublished(createReviewCommand.isPublished());
        this.review.setBook(new Book());
        this.bookId = createReviewCommand.getBookId();

        // Generate a unique identifier for the aggregate
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Constructs a new ReviewAggregate instance based on the UpdateReviewCommand.
     *
     * @param updateReviewCommand The UpdateReviewCommand containing the updated review details.
     */
    public ReviewAggregate(UpdateReviewCommand updateReviewCommand) {
        // Generate a unique identifier for the aggregate
        this.id = UUID.randomUUID().toString();
    }
}
