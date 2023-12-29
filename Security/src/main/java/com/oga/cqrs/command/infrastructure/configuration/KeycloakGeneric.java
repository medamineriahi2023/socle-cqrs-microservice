package com.oga.cqrs.command.infrastructure.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
@Configuration
@Data
@NoArgsConstructor
public class KeycloakGeneric {

  /**
   * a method tahtsplits the URL using the '/' character as a delimiter
   * @param locationHeader contaning the url of an api
   * @return the last part of the split array, expected to be an identifier
   */
  public String getIdFromLocationHeader(String locationHeader) {
    String[] parts = locationHeader.split("/");

    return parts[parts.length - 1];
  }

  /**
   * @param password
   * @return CredentialRepresentation object representing the password credential
   * CredentialRepresentation ,  a new instance of CredentialRepresentation
   * passwordCredentials.setTemporary(false),  it indicates that the password is a permanent credential
   * passwordCredentials.setType(CredentialRepresentation.PASSWORD), representing the password type.
   * passwordCredentials.setValue(password), it sets the value of the specific password
   */
  public static CredentialRepresentation createPasswordCredentials(String password) {
    CredentialRepresentation passwordCredentials = new CredentialRepresentation();
    passwordCredentials.setTemporary(false);
    passwordCredentials.setType(CredentialRepresentation.PASSWORD);
    passwordCredentials.setValue(password);
    return passwordCredentials;
  }
  public String getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    KeycloakAuthenticationToken keycloakAuthentication = (KeycloakAuthenticationToken) authentication;
    KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) keycloakAuthentication.getPrincipal();
    return keycloakPrincipal.getName();
  }
}
