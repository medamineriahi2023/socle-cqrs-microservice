package com.oga.workflow.command.rest.services;

// Importing required classes

import com.oga.workflow.command.rest.entities.EmailDetails;

// Interface
public interface EmailService {

    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}