package com.oga.workflow.command.rest.services.impl;

import com.oga.workflow.command.rest.entities.Review;
import com.oga.workflow.command.rest.services.StartMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service

public class StartMessageImpl implements StartMessage {
    private static final Log logger = LogFactory.getLog(StartMessageImpl.class);


    @Autowired
    RuntimeService runtimeService;
    @Override
    public void startPorcessByMessage(Review review) {
        logger.debug("Setting process variable");

        runtimeService.createMessageCorrelation("startMessage").
                setVariable("reviewer",review.getReviewer())
                .setVariable("content",review.getContent())
                .setVariable("book",review.getBook()).correlateStartMessage();
    }
}
