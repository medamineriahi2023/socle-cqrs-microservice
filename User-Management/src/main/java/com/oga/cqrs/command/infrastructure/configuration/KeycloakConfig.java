package com.oga.cqrs.command.infrastructure.configuration;

import com.oga.cqrs.command.rest.dto.TokenRequestLogin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Configuration: a configuration class to define beans
 * @Data, @NoArgsConstructor, @AllArgsConstructor: Lombok annotations  are used to generate getter, setter, equals, hashCode, and toString methods, constructors with no arguments and constructors with all arguments.
 * @Value: These annotations are used to inject values from configuration properties , in this case values are read from .env.
 */
@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeycloakConfig {

  @Value("${keycloak.auth-server-url}")
  public String serverURL;
  @Value("${keycloak.realm}")
  public String realm;
  @Value("${keycloak.resource}")
  public String clientID;
  @Value("${keycloak.credentials.secret}")
  public String clientSecret;

  @Value("${admin.username}")
  private String USERNAME;

  @Value("${admin.password}")
  private String PASSWORD;



  private static Keycloak keycloak = null;
  @Bean
  public KeycloakSpringBootConfigResolver KeycloakConfigResolver() {
    return new KeycloakSpringBootConfigResolver();
  }

  /**
   *  method is used to create a Keycloak instance.
   *  If keycloak is null, a new Keycloak instance is built using the provided configuration values and is returned.
   * @return Keycloak contaning the instance with corresponding connexion
   */
  public Keycloak getInstance() {
    if (keycloak == null) {
      return KeycloakBuilder.builder()
          .realm(realm)
          .serverUrl(serverURL)
          .clientId(clientID)
          .username(USERNAME).
          password(PASSWORD)
          .build();
    }
    return keycloak;
  }

  /**
   *  method is used to create a new Keycloak instance for any user that want to connect through specific username and password.
   *  If keycloak is null, a new Keycloak instance is built using the provided configuration values and is returned.
   * @return Keycloak contaning the instance with corresponding connexion
   */
  public Keycloak keycloakInstanceWithUsernameAndPaswword(TokenRequestLogin loginRequest) {
    return KeycloakBuilder.builder()
        .realm(realm)
        .serverUrl(serverURL)
        .clientId(clientID)
        .clientSecret(clientSecret)
        .username(loginRequest.getUsername())
        .password(loginRequest.getPassword()).build();
  }

}
