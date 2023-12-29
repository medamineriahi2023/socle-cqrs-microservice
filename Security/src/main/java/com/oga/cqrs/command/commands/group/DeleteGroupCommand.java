package com.oga.cqrs.command.commands.group;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * DeleteGroupCommand :a command for deleting a group , it extends a class named BaseCommand
 * id : the identifier of the command
 * idgroup : an identifier representing the group's id to be removed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteGroupCommand extends BaseCommand {

  private String	      id;

  private String idgroup;
}
