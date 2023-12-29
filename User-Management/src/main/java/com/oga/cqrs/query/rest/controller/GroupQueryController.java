package com.oga.cqrs.query.rest.controller;

import com.oga.cqrs.command.infrastructure.configuration.KeycloakConfig;
import com.oga.cqrs.command.infrastructure.configuration.KeycloakGeneric;
import com.oga.cqrs.command.rest.constant.Constant;
import com.oga.cqrs.query.queries.group.FindAllGroups;
import com.oga.cqrs.query.queries.group.FindGroupById;
import com.oga.cqrs.query.rest.dto.Group;
import com.oga.cqrs.query.rest.dto.Role;
import com.oga.cqrs.query.rest.dto.User;
import com.oga.cqrs.query.rest.repository.GroupRepository;
import com.oga.cqrs.query.rest.repository.RoleRepository;
import com.oga.cqrs.query.rest.repository.UserRepository;
import com.oga.cqrs.query.rest.response.QueryGroupResponse;
import com.oga.cqrsref.infrastructure.IQueryDispatcher;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@ComponentScan(basePackages="com.oga.cqrsref.controller")
@RestController
@RequestMapping(path = "/security/v1")
public class GroupQueryController {

  @Autowired
  private GroupRepository groupRepository;

  @Autowired
          private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  RealmResource realmResource;

  private final KeycloakGeneric keycloakGeneric;
  @Autowired
  public GroupQueryController(KeycloakConfig keycloakConfig, KeycloakGeneric keycloakGeneric) {
    this.keycloakGeneric=keycloakGeneric;
    this.realmResource = keycloakConfig.getInstance().realm(keycloakConfig.getRealm());
  }
  private final Logger logger = Logger.getLogger(GroupQueryController.class.getName());

  @Autowired
  private IQueryDispatcher queryDispatcher;


  /**
   * @return ResponseEntity containing a response object of type QueryGroupResponse
   * queryDispatcher.send(new FindAllGroups()) uses the queryDispatcher to send a query of type FindAllGroups
   * (groups == null ) this condition checks if the groups list is either null or empty
   * if groups list is null , a status of {@link HttpStatus#NO_CONTENT} is returned
   * if groups is not null , a status of {@link HttpStatus#OK} is returned
   * QueryGroupResponse.builder()  starts building a response object using the QueryGroupResponse builder
   * groups(groups): This sets the users property of the response object with the  list
   */
  @GetMapping("groups")
  public ResponseEntity<QueryGroupResponse> getAllGroups() {
    List<Group> groups = queryDispatcher.send(new FindAllGroups());
    if (groups == null ) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
    var response = QueryGroupResponse.builder()
        .groups(groups)

        .message(MessageFormat.format(Constant.GET_ALL_GROUPS_SUCCESS, groups.size()))
        .build();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
  /**
   * @return ResponseEntity containing a response object of type QueryGroupResponse
   * @param id
   * (Group) queryDispatcher.sendObject(new FindGroupById(id))  uses the queryDispatcher to send a query of type FindGroupById with the provided id to retrieve a single group
   * if group  is null , a status of {@link HttpStatus#NO_CONTENT} is returned
   * if group is not null , a status of {@link HttpStatus#OK} is returned
   * QueryGroupResponse.builder()  starts building a response object using the QueryGroupResponse builder
   * groups(groups): This sets the users property of the response object with the  list
   */
  @GetMapping(path = "groups/{id}")
  public ResponseEntity<QueryGroupResponse> getGroupById(@PathVariable String id) {

    Group group = (Group) queryDispatcher.sendObject(new FindGroupById(id));
    if (group == null ) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
    List<Group> groups =new ArrayList<>() {
    };
    groups.add(group);
    var response = QueryGroupResponse.builder()
        .groups(groups)
        .message(MessageFormat.format(Constant.GET_GROUP_BY_ID_SUCCESS, group.getId()))
        .build();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("groups/assignUsersToGroup")
  public ResponseEntity<?> assignUserToGroups(@RequestBody Map<String, Object> requestBody) {
    String groupId = (String) requestBody.get("groupId");
    List<String> userIds = (List<String>) requestBody.get("userIds");
    GroupResource groupResource = realmResource.groups().group(groupId);
    if (groupResource == null) {
      return new ResponseEntity<>("group with this id " + groupId + "is not found", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    List<UserRepresentation> existingUsers = groupResource.members();

    for (UserRepresentation user : existingUsers) {
      if (!userIds.contains(user.getId())) {
        UserResource userResource = realmResource.users().get(user.getId());
        if (userResource != null) {
          userResource.leaveGroup(groupId);
        }
      }
    }

    for (String userId : userIds) {
      UserResource userResource = realmResource.users().get(userId);
      if (userResource != null) {
        userResource.joinGroup(groupId);
      }
    }

    Group group = this.groupRepository.findById(groupId).get();
    List<User> users = this.userRepository.findByIdIn(userIds);


    group.setKcUsers(users);

    this.groupRepository.save(group);


    return new ResponseEntity<>(group, HttpStatus.CREATED);
  }


  @PostMapping("groups/assignRolesToGroup")
  public ResponseEntity<?> assignRolesToGroup(@RequestBody Map<String, Object> requestBody) {
    String groupId = (String) requestBody.get("groupId");
    List<String> roleIds = (List<String>) requestBody.get("roleIds");
    GroupResource groupResource = realmResource.groups().group(groupId);

    RoleMappingResource roleMappingResource = groupResource.roles();
    RoleScopeResource roleScopeResource = roleMappingResource.realmLevel();
    List<RoleRepresentation> existingRoles = roleMappingResource.realmLevel().listAll();
    roleScopeResource.remove(existingRoles);

    List<RoleRepresentation> rolesToAdd = new ArrayList<>();
    List<RoleRepresentation> allRoles = realmResource.roles().list();
    for (String roleId : roleIds) {
      RoleRepresentation roleRepresentation = allRoles.stream().filter(role -> role.getId().equals(roleId)).findFirst()
              .orElse(null);
      if (roleRepresentation != null) {
        rolesToAdd.add(roleRepresentation);
      }
    }
    roleScopeResource.add(rolesToAdd);


    Group group = this.groupRepository.findById(groupId).get();
    List<Role> roles = this.roleRepository.findByIdIn(roleIds);


    group.setRoles(roles);

    this.groupRepository.save(group);

    return new ResponseEntity<>(group, HttpStatus.CREATED);
  }
}
