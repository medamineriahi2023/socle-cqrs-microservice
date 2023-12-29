package com.oga.workflow.command.rest.conf;

import com.oga.cqrsref.infrastructure.CommandDispatcher;
import com.oga.workflow.command.commands.CreateBookCommand;
import com.oga.workflow.command.commands.CreateReviewCommand;
import com.oga.workflow.command.commands.UpdateReviewCommand;
import com.oga.workflow.command.handlers.impl.BookCommandHandler;
import com.oga.workflow.command.handlers.impl.ReviewCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Classe responsable de l'enregistrement des gestionnaires de commandes dans le CommandDispatcher.
 */
@Service
@ComponentScan("com.oga.cqrsref.infrastructure")
public class CommandHandlerRegistration {

    private final CommandDispatcher commandDispatcher;
    private final BookCommandHandler bookCommandHandler;
    private final ReviewCommandHandler reviewCommandHandler;

    /**
     * Constructeur de la classe CommandHandlerRegistration.
     *
     * @param commandDispatcher   CommandDispatcher utilisé pour dispatcher les commandes.
     * @param bookCommandHandler  BookCommandHandler responsable du traitement des commandes liées aux livres.
     * @param reviewCommandHandler ReviewCommandHandler responsable du traitement des commandes liées aux critiques.
     */
    @Autowired
    public CommandHandlerRegistration(CommandDispatcher commandDispatcher, BookCommandHandler bookCommandHandler, ReviewCommandHandler reviewCommandHandler) {
        this.commandDispatcher = commandDispatcher;
        this.bookCommandHandler = bookCommandHandler;
        this.reviewCommandHandler = reviewCommandHandler;
    }

    /**
     * Méthode exécutée après la construction de l'objet.
     * Enregistre les gestionnaires de commandes dans le CommandDispatcher.
     */
    @PostConstruct
    public void registerCommandHandlers() {
        commandDispatcher.registerHandler(CreateBookCommand.class, bookCommandHandler::handle);
        commandDispatcher.registerHandler(CreateReviewCommand.class, reviewCommandHandler::handle);
        commandDispatcher.registerHandler(UpdateReviewCommand.class, reviewCommandHandler::handle);
    }
}
