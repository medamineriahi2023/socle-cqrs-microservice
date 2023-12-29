package com.oga.workflow.command.rest.repository;

import com.oga.workflow.command.rest.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de repository pour l'entité Book.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
