package com.oga.workflow.command.rest.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/**
 * Classe de délégué pour la tâche "Review Not Published".
 */
@Component("ReviewNotPublished")
public class ReviewNotPublished implements JavaDelegate {

    /**
     * Méthode d'exécution du délégué.
     *
     * @param execution L'objet DelegateExecution pour accéder aux variables d'exécution.
     * @throws Exception En cas d'erreur lors de l'exécution de la tâche.
     */
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        execution.setVariable("Niveau2", 2);
    }
}
