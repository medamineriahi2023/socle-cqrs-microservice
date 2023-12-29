package com.oga.workflow.command.infrastructure.impl;

import com.oga.cqrsref.producer.EventProducer;
import com.oga.workflow.command.events.EventStoreRepository;
import com.oga.workflow.command.infrastructure.EventStore;
import com.oga.workflow.command.model.EventModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe qui fournit une implémentation de l'interface EventStore pour stocker les événements.
 */
@Service
@EnableMongoRepositories("com.oga.workflow.command.events")
public class MicroRefEventStore implements EventStore {

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private EventStoreRepository eventStoreRepository;

    /**
     * Sauvegarde un événement dans le store d'événements.
     *
     * @param eventModel Modèle d'événement à sauvegarder.
     */
    @Override
    public void saveEvents(EventModel eventModel) {
        eventStoreRepository.save(eventModel);
    }

    /**
     * Récupère les événements associés à un identifiant d'agrégat.
     *
     * @param aggregateId Identifiant de l'agrégat.
     * @return Liste des événements associés à l'agrégat.
     */
    @Override
    public List<Map<String, Object>> getEvents(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);

        return eventStream.stream().map(x -> x.getEventData()).collect(Collectors.toList());
    }

    /**
     * Récupère les identifiants des agrégats.
     *
     * @return Liste des identifiants des agrégats.
     */
    @Override
    public List<String> getAggregateIds() {
        var eventStream = eventStoreRepository.findAll();

        return eventStream.stream().map(EventModel::getAggregateIdentifier).distinct().collect(Collectors.toList());
    }

}
