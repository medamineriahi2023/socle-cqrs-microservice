package com.oga.workflow.command.rest.repository;

import com.oga.workflow.command.rest.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface de repository pour l'entité Review.
 */
public interface ReviewRepository extends JpaRepository<Review, Integer> {

}
