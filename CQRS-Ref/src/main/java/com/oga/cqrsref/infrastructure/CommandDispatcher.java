package com.oga.cqrsref.infrastructure;

import com.oga.cqrsref.commands.BaseCommand;
import com.oga.cqrsref.commands.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
    void send(BaseCommand command) ;
}
