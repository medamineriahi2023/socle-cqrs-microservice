package com.oga.cqrs.command.rest.controller;


import com.oga.cqrs.command.commands.role.*;
import com.oga.cqrs.command.domain.RoleAggregate;
import com.oga.cqrs.command.infrastructure.configuration.KeycloakConfig;
import com.oga.cqrs.command.infrastructure.configuration.KeycloakGeneric;
import com.oga.cqrs.command.rest.dto.Role;
import com.oga.cqrs.command.rest.response.BasicResponse;
import com.oga.cqrs.query.rest.repository.GroupRepository;
import com.oga.cqrs.query.rest.repository.RoleRepository;
import com.oga.cqrs.query.rest.repository.UserRepository;
import com.oga.cqrsref.handlers.EventSourcingHandler;
import com.oga.cqrsref.infrastructure.CommandDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static com.oga.cqrs.command.rest.constant.Constant.*;

@ComponentScan(basePackages="com.oga.cqrsref.controller")
@RestController
@RequestMapping(path = "/security/v1")
@Slf4j
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;
  private final CommandDispatcher commandDispatcher;

    RealmResource realmResource;

    private final KeycloakGeneric keycloakGeneric;
    @Autowired
    public RoleController(CommandDispatcher commandDispatcher, KeycloakConfig keycloakConfig, KeycloakGeneric keycloakGeneric) {
        this.commandDispatcher = commandDispatcher;
        this.keycloakGeneric=keycloakGeneric;
        this.realmResource = keycloakConfig.getInstance().realm(keycloakConfig.getRealm());
    }
  /**
   Handles a request to create a new role.
   @param role A role object representing the role to create.
   @return A ResponseEntity containing a BaseResponse object with a message and the ID of the new role.
   new CreateRoleCommand()  creates a new instance of CreateGroupCommand
   UUID.randomUUID().toString() generates a random UUID and converts it to a string used to keep the events with a unique id
   command.setId(id) ,  ID is set into the CreateRoleCommand
   command.setRole(role), role is set into the CreateRoleCommand
   commandDispatcher.send(command) dispatches the CreateRoleCommand to the commandDispatcher, which will handle the command and execute the logic associated with creating a role
   If the request is successful, a status of {@link HttpStatus#CREATED} is returned.
   If the request is unsuccessful due to a bad request, a status of {@link HttpStatus#BAD_REQUEST} is returned.
   If an error occurs during processing, a status of {@link HttpStatus#INTERNAL_SERVER_ERROR} is returned.
   */

  @PostMapping("roles")
  public ResponseEntity<BasicResponse> createRole(@Valid @RequestBody Role role) {
    CreateRoleCommand command=new CreateRoleCommand();
    var id = UUID.randomUUID().toString();
    command.setId(id);
    command.setRole(role);
    commandDispatcher.send(command);

    return new ResponseEntity<>(new BasicResponse(SUCCESS_MESSAGE_CREATION_ROLE, command.getId()), HttpStatus.CREATED);


  }
  /**
   Handles a request to update a  role.
   @param role A role object representing the role to update.
   @return A ResponseEntity containing a BaseResponse object with a message and the ID of the new role.
   new UpdateRoleCommand()  creates a new instance of UpdateRoleCommand
   UUID.randomUUID().toString() generates a random UUID and converts it to a string used to keep the events with a unique id
   command.setId(id) ,  ID is set into the UpdateRoleCommand
   command.setRole(role), role is set into the UpdateRoleCommand
   commandDispatcher.send(command) dispatches the UpdateRoleCommand to the commandDispatcher, which will handle the command and execute the logic associated with updating a role
   If the request is successful, a status of {@link HttpStatus#CREATED} is returned.
   If the request is unsuccessful due to a bad request, a status of {@link HttpStatus#BAD_REQUEST} is returned.
   If an error occurs during processing, a status of {@link HttpStatus#INTERNAL_SERVER_ERROR} is returned.
   */
  @PutMapping("roles")
  public ResponseEntity<BasicResponse> updateRole(@Valid @RequestBody  Role role) {
    UpdateRoleCommand command=new UpdateRoleCommand();
    var id = UUID.randomUUID().toString();
    command.setId(id);
    command.setRole(role);
    commandDispatcher.send(command);

    return new ResponseEntity<>(new BasicResponse(SUCCESS_MESSAGE_UPDATE_ROLE, id), HttpStatus.CREATED);
  }

  /**
   Handles a request to delete a  role.
   @param idrole A role object representing the role to delete.
   @return A ResponseEntity containing a BaseResponse object with a message and the ID of the new role.
   new DeleteRoleCommand()  creates a new instance of DeleteRoleCommand
   UUID.randomUUID().toString() generates a random UUID and converts it to a string used to keep the events with a unique id
   command.setId(id) ,  ID is set into the DeleteRoleCommand
   command.setIdrole(idrole) ,  role's id is set into the DeleteRoleCommand
   commandDispatcher.send(command) dispatches the DeleteRoleCommand to the commandDispatcher, which will handle the command and execute the logic associated with deleting a role
   If the request is successful, a status of {@link HttpStatus#CREATED} is returned.
   If the request is unsuccessful due to a bad request, a status of {@link HttpStatus#BAD_REQUEST} is returned.
   If an error occurs during processing, a status of {@link HttpStatus#INTERNAL_SERVER_ERROR} is returned.
   */
  @DeleteMapping("roles/{idrole}")
  public ResponseEntity<BasicResponse> deleteRole(@PathVariable(value = "idrole") String idrole) {
    DeleteRoleCommand command=new DeleteRoleCommand();
    var id = UUID.randomUUID().toString();
    command.setId(id);
    command.setIdrole(idrole);
    commandDispatcher.send(command);

    return new ResponseEntity<>(new BasicResponse(SUCCESS_MESSAGE_DELETE_ROLE, id), HttpStatus.CREATED);
  }

    @PostMapping("/roles/assignCompositeRolesForRole")
    public ResponseEntity<?> assignCompositeRolesForRole(@RequestBody Map<String, Object> requestBody) {
        String roleId = (String) requestBody.get("roleId");
        List<String> rolesIds = (List<String>) requestBody.get("rolesIds");

        RoleRepresentation role = realmResource.rolesById().getRole(roleId);
        RoleResource roleResource = realmResource.roles().get(role.getName());

        if (role == null) {
            return new ResponseEntity<>("Role with ID " + roleId + " not found.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Set<RoleRepresentation> existingComposites = roleResource.getRoleComposites();
        List<RoleRepresentation> compositesToRemove = existingComposites.stream()
                .filter(r -> !rolesIds.contains(r.getId())).collect(Collectors.toList());
        if (!compositesToRemove.isEmpty()) {
            roleResource.deleteComposites(compositesToRemove);
        }

        List<RoleRepresentation> compositesToAdd = rolesIds.stream().map(id -> realmResource.rolesById().getRole(id))
                .filter(Objects::nonNull).collect(Collectors.toList());
        roleResource.addComposites(compositesToAdd);

        com.oga.cqrs.query.rest.dto.Role role1 = this.roleRepository.findById(roleId).get();
        List<com.oga.cqrs.query.rest.dto.Role> permissions = new ArrayList<>();
        rolesIds.forEach(r ->{
            com.oga.cqrs.query.rest.dto.Role demo = new com.oga.cqrs.query.rest.dto.Role();
            demo.setId(r);
            permissions.add(demo);
        });
        role1.setPermissions(permissions);
        roleRepository.save(role1);

        return new ResponseEntity<>(role1,HttpStatus.CREATED);
    }

}


