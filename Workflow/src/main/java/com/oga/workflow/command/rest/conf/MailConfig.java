package com.oga.workflow.command.rest.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Classe de configuration pour la configuration du JavaMailSender.
 */
@Configuration
public class MailConfig {


    @Value("${EMAIL_USERNAME}")
    private String emailUsername;

    @Value("${EMAIL_PASSWORD}")
    private String emailPassword;

    @Value("${EMAIL_HOST}")
    private String emailHost;

    @Value("${EMAIL_PORT}")
    private int emailPort;
    /**
     * Configure le JavaMailSender pour envoyer des e-mails.
     *
     * @return JavaMailSender configuré.
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailHost); // Utiliser la variable d'environnement pour l'hôte du serveur de messagerie sortant
        mailSender.setPort(emailPort); // Utiliser la variable d'environnement pour le port du serveur de messagerie sortant
        mailSender.setUsername(emailUsername); // Utiliser la variable d'environnement pour l'adresse e-mail Gmail
        mailSender.setPassword(emailPassword); // Utiliser la variable d'environnement pour le mot de passe Gmail
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.transport.protocol", "smtp");

        return mailSender;
    }
}
