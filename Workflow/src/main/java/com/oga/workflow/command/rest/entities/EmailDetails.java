package com.oga.workflow.command.rest.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe représentant les détails d'un email.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {

    // Membres de données de la classe
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;

    // Les annotations lombok génèrent automatiquement les constructeurs, les méthodes getters/setters,
    // les méthodes toString, equals et hashCode.

}
