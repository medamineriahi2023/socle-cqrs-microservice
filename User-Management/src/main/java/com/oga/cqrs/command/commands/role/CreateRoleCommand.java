package com.oga.cqrs.command.commands.role;


import com.oga.cqrs.command.rest.dto.Role;
import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * CreateRoleCommand :a command for creating a role , it extends a class named BaseCommand
 * id : the identifier of the command
 * role : an object representing the role to be added
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateRoleCommand extends BaseCommand {
  private String	      id;
  private Role role;

}
