package com.oga.workflow.command.infrastructure.impl;

import com.oga.cqrsref.producer.EventProducer;
import com.oga.workflow.command.aggregate.BookAggregate;
import com.oga.workflow.command.infrastructure.EventSourcingHandler;
import com.oga.workflow.command.infrastructure.EventStore;
import com.oga.workflow.command.model.EventModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

/**
 * Gère l'événement sourcing pour la classe BookAggregate.
 */
@Service
public class BookEventHandler implements EventSourcingHandler<BookAggregate> {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private EventProducer eventProducer;

    /**
     * Constructeur de la classe BookEventHandler.
     *
     * @param eventStore     Store d'événements utilisé pour sauvegarder les événements liés aux livres.
     * @param eventProducer  Producteur d'événements utilisé pour publier les événements liés aux livres.
     */
    @Autowired
    public BookEventHandler(EventStore eventStore, EventProducer eventProducer) {
        this.eventStore = eventStore;
        this.eventProducer = eventProducer;
    }

    /**
     * Sauvegarde l'agrégat de livre et l'événement associé.
     *
     * @param aggregate Agrégat de livre à sauvegarder.
     * @param eventType Type d'événement associé.
     */
    @Override
    public void save(BookAggregate aggregate, String eventType) {
        // Création d'une map contenant le livre de l'agrégat
        HashMap<String, Object> myMap = new HashMap<>();
        myMap.put("book", aggregate.getBook());

        // Création de l'événement à sauvegarder
        EventModel event = EventModel.builder()
                .id(aggregate.getId())
                .aggregateIdentifier(aggregate.getId())
                .aggregateType(BookAggregate.AGGREGATE_TYPE)
                .eventType(eventType)
                .eventData(myMap)
                .timeStamp(new Date())
                .build();

        // Sauvegarde de l'événement dans le store d'événements
        eventStore.saveEvents(event);
    }

    @Override
    public BookAggregate getById(String id) {
        return null;
    }

    @Override
    public void republishEvents() {
        // TODO: Implémenter la logique pour republier les événements liés aux livres
    }
}
