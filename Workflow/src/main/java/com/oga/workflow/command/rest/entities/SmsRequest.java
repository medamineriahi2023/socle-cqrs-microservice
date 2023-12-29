package com.oga.workflow.command.rest.entities;

import lombok.Data;

/**
 * Classe représentant une demande d'envoi de SMS.
 */
@Data
public class SmsRequest {

    private String to;
    private String message;

    // Les annotations Lombok génèrent automatiquement les méthodes getters et setters, ainsi que d'autres méthodes telles que equals, hashCode et toString.

}
