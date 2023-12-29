package com.oga.workflow.command.handlers.impl;

import com.oga.cqrsref.commands.BaseCommand;

public interface ReviewCommandHandlerInterface {

    Integer handle(BaseCommand command);
    //int handleCreateReviewCommand(CreateReviewCommand command)
}
