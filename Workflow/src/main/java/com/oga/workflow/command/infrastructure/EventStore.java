package com.oga.workflow.command.infrastructure;

import com.oga.workflow.command.model.EventModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Interface pour le stockage des événements.
 */
@Service
public interface EventStore {

    /**
     * Sauvegarde un modèle d'événement.
     *
     * @param eventModel Modèle d'événement à sauvegarder.
     */
    void saveEvents(EventModel eventModel);

    /**
     * Récupère les événements associés à un identifiant d'agrégat.
     *
     * @param aggregateId Identifiant de l'agrégat.
     * @return Liste des événements associés à l'agrégat.
     */
    List<Map<String, Object>> getEvents(String aggregateId);

    /**
     * Récupère les identifiants des agrégats.
     *
     * @return Liste des identifiants des agrégats.
     */
    List<String> getAggregateIds();
}
