package com.oga.cqrs.command.commands.user;

import com.oga.cqrs.command.rest.dto.Login;
import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UpdatePasswordCommand :a command for updating a user's password , it extends a class named BaseCommand
 * id : the identifier of the command
 * login : an object representing the password to be updated
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordCommand extends BaseCommand {

  private String	      id;
  private Login login;


}
