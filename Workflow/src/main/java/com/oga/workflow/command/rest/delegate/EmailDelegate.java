package com.oga.workflow.command.rest.delegate;

import com.oga.workflow.command.rest.entities.EmailDetails;
import com.oga.workflow.command.rest.services.EmailService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * Classe de délégué pour la tâche "Email".
 */
@ComponentScan
@Component("EmailDelegate")
public class EmailDelegate implements JavaDelegate {

    private final EmailService emailService;
    @Value("${EMAIL_RECIPIENT}")
    private String emailRecipient;

    @Value("${EMAIL_SUBJECT}")
    private String emailSubject;

    @Value("${EMAIL_MESSAGE}")
    private String emailMessage;
    /**
     * Constructeur du délégué EmailDelegate.
     *
     * @param emailService EmailService utilisé pour envoyer les e-mails.
     */
    @Autowired
    public EmailDelegate(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Méthode d'exécution du délégué.
     *
     * @param execution L'objet DelegateExecution pour accéder aux variables d'exécution.
     * @throws Exception En cas d'erreur lors de l'exécution de la tâche.
     */
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(emailRecipient);
        emailDetails.setSubject(emailSubject);
        emailDetails.setMsgBody(emailMessage);
        String status = emailService.sendSimpleMail(emailDetails);
    }
}
