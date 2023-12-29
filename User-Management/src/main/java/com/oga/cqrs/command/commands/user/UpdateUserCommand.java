package com.oga.cqrs.command.commands.user;

import com.oga.cqrs.command.rest.dto.User;
import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UpdateUserCommand :a command for updating a user , it extends a class named BaseCommand
 * id : the identifier of the command
 * user : an object representing the user to be updated
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UpdateUserCommand extends BaseCommand {

  private String	      id;
  private User user;

}
