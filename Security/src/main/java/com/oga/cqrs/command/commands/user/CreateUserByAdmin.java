package com.oga.cqrs.command.commands.user;

import com.oga.cqrs.command.rest.dto.User;
import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * CreateUserByAdmin :a command for creating a user by admin, it extends a class named BaseCommand
 * id : the identifier of the command
 * user : an object representing the user to be added
 */

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateUserByAdmin extends BaseCommand {

  private String	      id;
  private User user;


}
