package com.oga.cqrs.command.commands.user;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * AffectRolesCommand :a command for roles affectation, it extends a class named BaseCommand
 * id : the identifier of the command
 * userId : the identifier of a specific user
 * rolesIds: array of roles id to affect to user
 */

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AffectRolesCommand extends BaseCommand {
  private String	      id;
  private String userId;
  private List<String> roleIds;
}
