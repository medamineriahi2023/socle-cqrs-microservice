package com.oga.workflow.command.rest.delegate;

import com.oga.cqrsref.infrastructure.CommandDispatcher;
import com.oga.workflow.command.commands.CreateReviewCommand;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Classe de délégué pour la tâche "Create Book Review".
 */
@ComponentScan
@Component("CreateBookReviewDelegate")
public class CreateBookReviewDelegate implements JavaDelegate {

    private final CommandDispatcher commandDispatcher;

    /**
     * Constructeur du délégué CreateBookReviewDelegate.
     *
     * @param commandDispatcher CommandDispatcher utilisé pour dispatcher les commandes.
     */
    @Autowired
    public CreateBookReviewDelegate(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    /**
     * Méthode d'exécution du délégué.
     *
     * @param execution L'objet DelegateExecution pour accéder aux variables d'exécution.
     * @throws Exception En cas d'erreur lors de l'exécution de la tâche.
     */
    @Override
    public void execute(DelegateExecution execution) throws Exception {

        String id = UUID.randomUUID().toString();
        CreateReviewCommand command = new CreateReviewCommand(id, Integer.parseInt(String.valueOf(execution.getVariable("book")))
                ,String.valueOf(execution.getVariable("reviewer")), String.valueOf(execution.getVariable("content")),
                false);
         commandDispatcher.send(command);
       execution.setVariable("reviewId", command.getBookId());
          execution.setVariable("Niveau",0);
    }
}
