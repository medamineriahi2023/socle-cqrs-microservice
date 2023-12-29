package com.oga.cqrs.command.events.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrs.command.rest.dto.TokenRequestLogin;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
 * TokenRequested  represents an event related to generation of a user's token, It extends a class named BaseEvent.
 * TokenRequested  has two instance variables: identifier and login.
 * identifier : the identifier of the command related to TokenRequested
 * TokenRequest : an object representing username and the password
 */

/**
 * TokenRequested  represents an event related to generation of a user's token, It extends a class named BaseEvent.
 * TokenRequested  has two instance variables: identifier and login.
 * identifier : the identifier of the command related to TokenRequested
 * TokenRequest : an object representing username and the password
 */


@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize

public class TokenRequested extends BaseEvent {
  private String identifier;
  private TokenRequestLogin TokenRequest;
}

