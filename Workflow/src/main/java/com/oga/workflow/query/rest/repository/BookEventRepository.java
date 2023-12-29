package com.oga.workflow.query.rest.repository;


import com.oga.workflow.command.rest.entities.Book;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;



@Primary
public interface BookEventRepository extends MongoRepository<Book, String> {
    // Add custom query methods if needed

}