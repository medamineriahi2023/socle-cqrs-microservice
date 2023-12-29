package com.oga.cqrs.command.commands.user;

import com.oga.cqrs.command.rest.dto.TokenRequestLogin;
import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * CreateTokenRequestCommand :a command for generating a token, it extends a class named BaseCommand
 * id : the identifier of the command
 * TokenRequest : an object representing the username and the password
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTokenRequestCommand extends BaseCommand {

  private String	      id;
  private TokenRequestLogin TokenRequest;


}
