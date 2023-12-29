package com.oga.workflow.command.rest.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Classe représentant l'entité Review.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private String reviewer;
    private String content;
    private boolean published;
    // Ajoutez plus d'attributs selon vos besoins

    // Les annotations Lombok génèrent automatiquement les méthodes getters, setters et d'autres méthodes comme equals, hashCode et toString.

    // Getters and Setters for book, reviewer, content, published properties

    // Ajoutez d'autres getters et setters pour les attributs supplémentaires

}
