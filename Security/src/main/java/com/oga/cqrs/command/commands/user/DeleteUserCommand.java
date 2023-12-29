package com.oga.cqrs.command.commands.user;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DeleteUserCommand :a command for deleting a user , it extends a class named BaseCommand
 * id : the identifier of the command
 * iduser : an identifier representing the user's id to be removed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserCommand extends BaseCommand {

  private String	      id;

    private String iduser;
}
