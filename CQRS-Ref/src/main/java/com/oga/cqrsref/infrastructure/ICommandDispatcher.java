package com.oga.cqrsref.infrastructure;

import com.oga.cqrsref.commands.BaseCommand;
import com.oga.cqrsref.commands.CommandHandlerMethod;
import com.oga.cqrsref.exceptions.CommandHandlerException;
import com.oga.cqrsref.exceptions.MultipleCommandHandlersException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class ICommandDispatcher implements CommandDispatcher {
  private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();


  /**
   * Registers a command handler for a given command type.
   *
   * @param <T>     the type of the command
   * @param type    the class object representing the type of the command
   * @param handler the command handler method for the given command type
   */

  @Override
  public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
    var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
    handlers.add(handler);
  }

  /**
   * Sends a command to its corresponding handler.
   *
   * @param command the command to be sent
   */
  @Override
  public void send(BaseCommand command) {
    var handlers = routes.get(command.getClass());
    if (handlers == null || handlers.isEmpty()) {
      throw new CommandHandlerException();
    }
    if (handlers.size() > 1) {
      throw new MultipleCommandHandlersException();
    }

    handlers.get(0).handle(command);
  }

}
