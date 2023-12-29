package com.oga.workflow.query.event.impl; /**
 * The CreateBookEvent class is an implementation of the EventHandler interface
 * that handles the creation of a book event by saving it to the database.
 *//*

package com.workflow.query.event.impl;

import com.oga.workflow.command.model.EventModel;
import com.workflow.query.event.EventHandler;
import com.workflow.query.rest.dto.Book;
import com.workflow.query.rest.repository.BookEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Transactional
@Service
@EnableMongoRepositories("com.workflow.query.rest.repository")
public class CreateBookEvent implements EventHandler {

    @Autowired
    BookEventRepository eventRepository;

    */
/**
     * Handles the book creation event by extracting the book data from the event,
     * creating a new Book object, and saving it to the database.
     *
     * @param event The event model representing the book creation event.
     *//*

    @Override
    public void on(EventModel event) {
        // Extract the book data from the event
        Map<String, Object> bookData = (Map<String, Object>) event.getEventData().get("book");

        // Create a new Book object
        Book book = new Book();
        book.setTitle((String) bookData.get("title"));
        book.setAuthor((String) bookData.get("author"));

        // Save the book to the database
        eventRepository.save(book);
    }
}
*/
