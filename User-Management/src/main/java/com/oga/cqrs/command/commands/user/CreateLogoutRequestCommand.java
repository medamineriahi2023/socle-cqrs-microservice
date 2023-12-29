package com.oga.cqrs.command.commands.user;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CreateLogoutRequestCommand :a command for logout, it extends a class named BaseCommand
 * id : the identifier of the command
 * iduser : the identifier of a specific user
 */


@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateLogoutRequestCommand  extends BaseCommand {
  private String	      id;
  private String iduser;
}
