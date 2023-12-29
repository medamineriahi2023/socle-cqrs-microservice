package com.oga.cqrs.query.rest.controller;

import com.oga.cqrs.command.infrastructure.configuration.KeycloakConfig;
import com.oga.cqrs.command.infrastructure.configuration.KeycloakGeneric;
import com.oga.cqrs.command.rest.constant.Constant;
import com.oga.cqrs.query.queries.role.FindAllRoles;
import com.oga.cqrs.query.queries.role.FindRoleById;
import com.oga.cqrs.query.rest.dto.Role;
import com.oga.cqrs.query.rest.repository.GroupRepository;
import com.oga.cqrs.query.rest.repository.RoleRepository;
import com.oga.cqrs.query.rest.repository.UserRepository;
import com.oga.cqrs.query.rest.response.QueryRoleResponse;
import com.oga.cqrsref.infrastructure.IQueryDispatcher;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ComponentScan(basePackages="com.oga.cqrsref.controller")
@RestController
@RequestMapping(path = "/security/v1")
public class RoleQueryController {
  private final Logger logger = Logger.getLogger(RoleQueryController.class.getName());


  @Autowired
  private IQueryDispatcher queryDispatcher;
  /**
   * @return ResponseEntity containing a response object of type QueryRoleResponse
   * queryDispatcher.send(new FindAllRoles()) uses the queryDispatcher to send a query of type FindAllRoles
   * (roles == null ) this condition checks if the roles list is either null or empty
   * if roles list is null , a status of {@link HttpStatus#NO_CONTENT} is returned
   * if roles is not null , a status of {@link HttpStatus#OK} is returned
   * QueryRoleResponse.builder()  starts building a response object using the QueryRoleResponse builder
   * roles(roles): This sets the roles property of the response object with the  list
   */
  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  RealmResource realmResource;

  private final KeycloakGeneric keycloakGeneric;
  public static String client;
  @Autowired
  public RoleQueryController(KeycloakConfig keycloakConfig, KeycloakGeneric keycloakGeneric) {
    this.keycloakGeneric=keycloakGeneric;
    this.realmResource = keycloakConfig.getInstance().realm(keycloakConfig.getRealm());
    client= keycloakConfig.getClientID();
  }
  @GetMapping("roles")
  public ResponseEntity<QueryRoleResponse> getAllRoles() {
    List<Role> roles = queryDispatcher.send(new FindAllRoles());
    if (roles == null ) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
    var response = QueryRoleResponse.builder()
        .roles(roles)

        .message(MessageFormat.format(Constant.GET_ALL_ROLES_SUCCESS, roles.size()))
        .build();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * @return ResponseEntity containing a response object of type QueryRoleResponse
   * @param id
   * (Role) queryDispatcher.sendObject(new FindRoleById(id)) uses the queryDispatcher to send a query of type FindRoleById with the provided id to retrieve a single role
   * if role  is null , a status of {@link HttpStatus#NO_CONTENT} is returned
   * if role is not null , a status of {@link HttpStatus#OK} is returned
   * QueryRoleResponse.builder()  starts building a response object using the QueryRoleResponse builder
   * roles(roles): This sets the roles property of the response object with the  list
   */
  @GetMapping(path = "roles/{id}")
  public ResponseEntity<QueryRoleResponse> getRoleById(@PathVariable String id) {

    Role role = (Role) queryDispatcher.sendObject(new FindRoleById(id));
    if (role == null ) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
    List<Role> roles =new ArrayList<>() {
    };
    roles.add(role);
    var response = QueryRoleResponse.builder()
        .roles(roles)
        .message(MessageFormat.format(Constant.GET_ROLE_BY_ID_SUCCESS, role.getId()))
        .build();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping(path = "roles/permissions")
  public ResponseEntity<List<Role>> getPermissions() {

    ClientRepresentation clientRepresentation = realmResource.clients().findByClientId(client)
            .get(0);
    List<RoleRepresentation> roleRepresentations = realmResource.clients().get(clientRepresentation.getId()).roles()
            .list();
    List<Role> roles = new ArrayList<>();

    for (RoleRepresentation roleRepresentation : roleRepresentations) {
      Role role = new Role();
      role.setId(roleRepresentation.getId());
      role.setName(roleRepresentation.getName());
      roles.add(role);
    }

    return new ResponseEntity<>(roles, HttpStatus.OK);
  }

}
