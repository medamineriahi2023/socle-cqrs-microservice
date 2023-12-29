package com.oga.cqrs.command.rest.controller;

import com.oga.cqrs.command.commands.group.*;
import com.oga.cqrs.command.rest.dto.Group;
import com.oga.cqrs.command.rest.response.BasicResponse;
import com.oga.cqrsref.infrastructure.CommandDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static com.oga.cqrs.command.rest.constant.Constant.*;

@ComponentScan(basePackages="com.oga.cqrsref.controller")
@RestController
@RequestMapping(path = "/security/v1")
@Slf4j
public class GroupController {

  private final CommandDispatcher commandDispatcher;
  @Autowired
  public GroupController(CommandDispatcher commandDispatcher) {
    this.commandDispatcher = commandDispatcher;
  }
  /**
   Handles a request to create a new group.
   @param group A group object representing the group to create.
   @return A ResponseEntity containing a BaseResponse object with a message and the ID of the new group.
   new CreateGroupCommand()  creates a new instance of CreateGroupCommand
   UUID.randomUUID().toString() generates a random UUID and converts it to a string used to keep the events with a unique id
   command.setId(id) ,  ID is set into the CreateGroupCommand
   command.setGroup(group) , group is set into the CreateGroupCommand
   commandDispatcher.send(command) dispatches the CreateGroupCommand to the commandDispatcher, which will handle the command and execute the logic associated with creating a group
   If the request is successful, a status of {@link HttpStatus#CREATED} is returned.
   If the request is unsuccessful due to a bad request, a status of {@link HttpStatus#BAD_REQUEST} is returned.
   If an error occurs during processing, a status of {@link HttpStatus#INTERNAL_SERVER_ERROR} is returned.
   */

  @PostMapping("groups")
  public ResponseEntity<BasicResponse> createGroup(@Valid @RequestBody Group group) {
    CreateGroupCommand command=new CreateGroupCommand();
    var id = UUID.randomUUID().toString();
    command.setId(id);
    command.setGroup(group);
    commandDispatcher.send(command);

    return new ResponseEntity<>(new BasicResponse(SUCCESS_MESSAGE_CREATION_GROUP, command.getId()), HttpStatus.CREATED);


  }

  /**
   Handles a request to update a group.
   @param group A User object representing the group to update.
   @return A ResponseEntity containing a BaseResponse object with a message and the ID of the new group.
   new UpdateGroupCommand()  creates a new instance of UpdateGroupCommand
   UUID.randomUUID().toString() generates a random UUID and converts it to a string used to keep the events with a unique id
   command.setId(id) ,  ID is set into the UpdateGroupCommand
   command.setGroup(group) , group is set into the UpdateGroupCommand
   commandDispatcher.send(command) dispatches the UpdateGroupCommand to the commandDispatcher, which will handle the command and execute the logic associated with updating a group
   If the request is successful, a status of {@link HttpStatus#CREATED} is returned.
   If the request is unsuccessful due to a bad request, a status of {@link HttpStatus#BAD_REQUEST} is returned.
   If an error occurs during processing, a status of {@link HttpStatus#INTERNAL_SERVER_ERROR} is returned.
   */
  @PutMapping("groups")
  public ResponseEntity<BasicResponse> updateGroup(@Valid @RequestBody Group group) {
    UpdateGroupCommand command=new UpdateGroupCommand();
    var id = UUID.randomUUID().toString();
    command.setId(id);
    command.setGroup(group);
    commandDispatcher.send(command);
    return new ResponseEntity<>(new BasicResponse(SUCCESS_MESSAGE_UPDATE_GROUP, command.getId()), HttpStatus.CREATED);
  }
  /**
   Handles a request to delete a  group.
   @param idgroup A group object representing the group to delete.
   @return A ResponseEntity containing a BaseResponse object with a message and the ID of the  group.
   new DeleteGroupCommand()  creates a new instance of DeleteGroupCommand
   UUID.randomUUID().toString() generates a random UUID and converts it to a string used to keep the events with a unique id
   command.setId(id) ,  ID is set into the DeleteGroupCommand
   command.setIdgroup(idgroup) ,  group's id is set into the DeleteGroupCommand
   commandDispatcher.send(command) dispatches the DeleteUserCommand to the commandDispatcher, which will handle the command and execute the logic associated with deleting a group
   If the request is successful, a status of {@link HttpStatus#CREATED} is returned.
   If the request is unsuccessful due to a bad request, a status of {@link HttpStatus#BAD_REQUEST} is returned.
   If an error occurs during processing, a status of {@link HttpStatus#INTERNAL_SERVER_ERROR} is returned.
   */
  @DeleteMapping("groups/{idgroup}")
  public ResponseEntity<BasicResponse> deleteGroup(@PathVariable(value = "idgroup") String idgroup ) {
    DeleteGroupCommand command=new DeleteGroupCommand();
    var id = UUID.randomUUID().toString();
    command.setId(id);
    command.setIdgroup(idgroup);
    commandDispatcher.send(command);
    return new ResponseEntity<>(new BasicResponse(SUCCESS_MESSAGE_DELETE_GROUP, command.getId()), HttpStatus.CREATED);
  }
}
