package com.oga.workflow.command.rest.services;

import com.oga.workflow.command.rest.entities.Review;


public interface StartMessage {
    void startPorcessByMessage(Review requestDetail);
}
