package com.oga.cqrs.command.commands.user;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * ForgetPasswordCommand :a command for password forgotten, it extends a class named BaseCommand
 * id : the identifier of the command
 * username : the username of a specific user
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgetPasswordCommand extends BaseCommand {
  private String	      id;
  private String username;
}

