package com.oga.workflow.query.event.impl;

import com.oga.workflow.command.events.impl.BookCreatedEvent;
import com.oga.workflow.command.rest.entities.Book;
import com.oga.workflow.query.event.EventHandlerBook;
import com.oga.workflow.query.rest.repository.BookEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
@EnableMongoRepositories("com.oga.workflow.query.rest.repository")
public class BookEventsHandler implements EventHandlerBook {

    @Autowired
    private BookEventRepository bookEventRepository;
    @Override
    public void on(BookCreatedEvent createBookEvent) {
        Book book= new Book(Integer.parseInt(createBookEvent.getBookId()) ,createBookEvent.getTitle(),createBookEvent.getAuthor());

        bookEventRepository.save(book);
    }
}
