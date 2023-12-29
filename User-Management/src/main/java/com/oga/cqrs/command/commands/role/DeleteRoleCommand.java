package com.oga.cqrs.command.commands.role;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * DeleteGroupCommand :a command for deleting a role , it extends a class named BaseCommand
 * id : the identifier of the command
 * idrole : an identifier representing the role's id to be removed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteRoleCommand extends BaseCommand {

  private String	      id;

  private String idrole;
}
