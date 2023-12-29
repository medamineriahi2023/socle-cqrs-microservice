package com.oga.cqrs.command.commands.group;

import com.oga.cqrs.command.rest.dto.Group;
import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * CreateGroupCommand :a command for creating a group by admin, it extends a class named BaseCommand
 * id : the identifier of the command
 * group : an object representing the group to be added
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGroupCommand extends BaseCommand {

  private String	      id;
  private Group group;


}

