package com.oga.workflow.command.handlers;

import com.oga.cqrsref.commands.BaseCommand;
import com.oga.workflow.command.commands.CreateBookCommand;

public interface BookCommandHandlerInterface {

    Integer handle(BaseCommand command);
    Integer handleCreateBookCommand(CreateBookCommand command);

}
