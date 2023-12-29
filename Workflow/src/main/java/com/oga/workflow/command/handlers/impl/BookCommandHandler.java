package com.oga.workflow.command.handlers.impl;

import com.oga.cqrsref.commands.BaseCommand;
import com.oga.cqrsref.producer.EventProducer;
import com.oga.workflow.command.aggregate.BookAggregate;
import com.oga.workflow.command.commands.CreateBookCommand;
import com.oga.workflow.command.events.impl.BookCreatedEvent;
import com.oga.workflow.command.handlers.BookCommandHandlerInterface;
import com.oga.workflow.command.infrastructure.impl.BookEventHandler;
import com.oga.workflow.command.model.EventModel;
import com.oga.workflow.command.rest.entities.Book;
import com.oga.workflow.command.rest.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
@EnableJpaRepositories("com.oga.workflow.command.rest.repository")
@ComponentScan("com.oga.cqrsref.producer")
public class BookCommandHandler implements BookCommandHandlerInterface {

    final    BookRepository bookRepository;
    final EventProducer eventProducer;

    final    BookEventHandler bookEventHandler;

    /**
     * Constructeur de la classe BookCommandHandler.
     *
     * @param bookRepository   Repository utilisé pour interagir avec la base de données des livres.
     * @param eventProducer    Producteur d'événements utilisé pour publier les événements liés aux livres.
     * @param bookEventHandler Gestionnaire d'événements des livres utilisé pour traiter les événements liés aux livres.
     */
    @Autowired
    public BookCommandHandler(BookRepository bookRepository, EventProducer eventProducer, BookEventHandler bookEventHandler) {
        this.bookRepository = bookRepository;
        this.eventProducer = eventProducer;
        this.bookEventHandler = bookEventHandler;
    }

    /**
     * Gère la commande spécifiée et renvoie un identifiant de livre.
     *
     * @param command Commande à traiter.
     * @return Identifiant du livre.
     * @throws IllegalArgumentException si le type de commande n'est pas pris en charge.
     */
    @Override
    public Integer handle(BaseCommand command) {
        int bookId = 0;
        if (command instanceof CreateBookCommand) {
            bookId = handleCreateBookCommand((CreateBookCommand) command);
        } else {
            throw new IllegalArgumentException("Unsupported command type");
        }
        return bookId;
    }

    /**
     * Traite la commande CreateBookCommand spécifiée et renvoie l'identifiant du livre créé.
     *
     * @param command Commande CreateBookCommand à traiter.
     * @return Identifiant du livre créé.
     */
    public Integer handleCreateBookCommand(CreateBookCommand command) {
        // Création d'un agrégat de livre à partir de la commande
        BookAggregate bookAggregate = new BookAggregate(command);

        // Création d'un nouvel objet Book à partir des informations de la commande
        Book book = new Book();
        book.setAuthor(command.getAuthor());
        book.setTitle(command.getTitle());

        // Sauvegarde du livre dans le repository
        book = bookRepository.save(book);

        // Création d'une map contenant le livre
        HashMap<String, Object> myMap = new HashMap<>();
        myMap.put("book", book);

        // Génération d'un identifiant unique pour l'événement
        var event = EventModel.builder()
                .id(bookAggregate.getId())
                .aggregateIdentifier(bookAggregate.getId())
                .aggregateType(BookAggregate.AGGREGATE_TYPE)
                .eventType(BookCreatedEvent.EVENT_TYPE)
                .eventData(myMap)
                .timeStamp(new Date())
                .build();

        BookCreatedEvent bookCreatedEvent =new BookCreatedEvent(String.valueOf(book.getId()),book.getTitle(),book.getAuthor());
        // Publication de l'événement BookCreatedEvent
        eventProducer.produce(BookCreatedEvent.EVENT_TYPE, bookCreatedEvent);

        // Sauvegarde de l'agrégat de livre dans le gestionnaire d'événements
        bookEventHandler.save(bookAggregate, BookCreatedEvent.EVENT_TYPE);

        // Renvoie l'identifiant du livre créé
        return book.getId();
    }
}
