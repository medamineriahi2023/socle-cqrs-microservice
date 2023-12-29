package com.oga.workflow.command.rest.delegate;

import com.oga.cqrsref.infrastructure.CommandDispatcher;
import com.oga.workflow.command.commands.UpdateReviewCommand;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe de délégué pour la tâche "Publish Review".
 */
@Component("PublishReviewDelegate")
public class PublishReviewDelegate implements JavaDelegate {

    private final CommandDispatcher commandDispatcher;

    /**
     * Constructeur du délégué PublishReviewDelegate.
     *
     * @param commandDispatcher CommandDispatcher utilisé pour dispatcher les commandes.
     */
    @Autowired
    public PublishReviewDelegate(CommandDispatcher commandDispatcher) {
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


        commandDispatcher.send(new UpdateReviewCommand(execution.getBusinessKey(),
                (int) execution.getVariable("reviewId"),
                (String)execution.getVariable("content"),true));
    }
}
