/**
 * The SmsService class provides a service for sending SMS messages using the Twilio API.
 */
package com.oga.workflow.command.rest.services.impl;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.oga.workflow.command.rest.entities.SmsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@Slf4j
public class SmsService {

    // Twilio account SID
    @Value("${ACCOUNT_SID}")
    private String accountSid;

    // Twilio authentication token
    @Value("${AUTH_TOKEN}")
    private String authToken;

    // Phone number used as the sender

    @Value("${FROM_NUMBER}")
    private  String fromNumber;

    /**
     * Sends an SMS message using the Twilio API.
     *
     * @param sms The SMS request object containing the recipient's phone number and the message content.
     */
    public void send(SmsRequest sms) {
        // Initialize the Twilio library with the account SID and auth token
        Twilio.init(accountSid, authToken);

        // Create a new MessageCreator object with the recipient's phone number, sender's phone number, and message content
        Message message = Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(fromNumber), sms.getMessage())
                .create();

        // Print the unique resource ID of the sent message
       log.info("Sent message SID: " + message.getSid());
    }

    /**
     * Returns the sender's phone number.
     *
     * @return The sender's phone number.
     */
    public String getFROM_NUMBER() {
        return this.fromNumber;
    }
}
