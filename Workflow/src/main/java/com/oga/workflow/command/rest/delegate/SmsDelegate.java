package com.oga.workflow.command.rest.delegate;

import com.oga.workflow.command.rest.entities.SmsRequest;
import com.oga.workflow.command.rest.services.impl.SmsService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * Classe de délégué pour l'envoi de SMS.
 */
@Component("SmsDelegate")
public class SmsDelegate implements JavaDelegate {

    private final String TOPIC_DESTINATION = "/lesson/sms";

    @Autowired
    SmsService service;

    @Autowired
    private SimpMessagingTemplate webSocket;
    @Value("${SMS_NUMBER}")
    private String smsNumber;

    @Value("${SMS_MESSAGE}")
    private String smsMessage;
    /**
     * Méthode d'exécution du délégué.
     *
     * @param delegateExecution L'objet DelegateExecution pour accéder aux variables d'exécution.
     * @throws Exception En cas d'erreur lors de l'exécution du délégué.
     */
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        SmsRequest sms = new SmsRequest();
        sms.setMessage("you have new review !!");
        sms.setTo("+21695349410");

        try {
            service.send(sms);
        } catch (Exception e) {
            webSocket.convertAndSend(TOPIC_DESTINATION, " Error sending the SMS: " + e.getMessage());
            throw e;
        }

        webSocket.convertAndSend(TOPIC_DESTINATION, " SMS has been sent!: " + sms.getTo());
    }
}
