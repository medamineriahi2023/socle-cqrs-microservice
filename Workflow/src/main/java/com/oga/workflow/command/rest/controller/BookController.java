package com.oga.workflow.command.rest.controller;

import com.oga.cqrsref.infrastructure.CommandDispatcher;
import com.oga.workflow.command.commands.CreateBookCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur pour les opérations liées aux livres.
 */
@RestController
@RequestMapping("/workflow/books")
public class BookController {

    private final CommandDispatcher commandDispatcher;

    /**
     * Constructeur du contrôleur BookController.
     *
     * @param commandDispatcher CommandDispatcher utilisé pour dispatcher les commandes.
     */
    @Autowired
    public BookController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    /**
     * Endpoint pour la création d'un livre.
     *
     * @param createBookCommand Commande de création du livre.
     * @return ResponseEntity représentant le résultat de la requête.
     */
    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody CreateBookCommand createBookCommand) {
        commandDispatcher.send(createBookCommand);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
