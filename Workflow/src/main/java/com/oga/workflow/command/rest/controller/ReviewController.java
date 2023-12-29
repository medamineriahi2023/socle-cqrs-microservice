package com.oga.workflow.command.rest.controller;

import com.oga.cqrsref.infrastructure.CommandDispatcher;
import com.oga.workflow.command.commands.CreateReviewCommand;
import com.oga.workflow.command.commands.UpdateReviewCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur pour les opérations liées aux critiques.
 */
@RestController
@RequestMapping("/workflow/reviews")
public class ReviewController {

    private final CommandDispatcher commandDispatcher;

    /**
     * Constructeur du contrôleur ReviewController.
     *
     * @param commandDispatcher CommandDispatcher utilisé pour dispatcher les commandes.
     */
    @Autowired
    public ReviewController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    /**
     * Endpoint pour la création d'une critique.
     *
     * @param createReviewCommand Commande de création de la critique.
     * @return ResponseEntity représentant le résultat de la requête avec l'identifiant de la critique créée.
     */
    @PostMapping("/create")
    public ResponseEntity<Integer> createReview(@RequestBody CreateReviewCommand createReviewCommand) {
         commandDispatcher.send(createReviewCommand);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * Endpoint pour la mise à jour d'une critique.
     *
     * @param updateReviewCommand Commande de mise à jour de la critique.
     * @return ResponseEntity représentant le résultat de la requête.
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateReview(@RequestBody UpdateReviewCommand updateReviewCommand) {
        commandDispatcher.send(updateReviewCommand);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
