package com.oga.workflow.command.infrastructure;

import org.springframework.stereotype.Service;

/**
 * Interface générique pour les gestionnaires d'événements sourcing.
 *
 * @param <T> Type de l'agrégat associé à l'événement sourcing.
 */
@Service
public interface EventSourcingHandler<T> {

    /**
     * Sauvegarde l'agrégat et l'événement associé.
     *
     * @param aggregate Agrégat à sauvegarder.
     * @param eventType Type d'événement associé.
     */
    void save(T aggregate, String eventType);

    /**
     * Récupère l'agrégat à partir de son identifiant.
     *
     * @param id Identifiant de l'agrégat.
     * @return L'agrégat correspondant à l'identifiant.
     */
    T getById(String id);

    /**
     * Republie les événements associés à l'agrégat.
     */
    void republishEvents();
}
