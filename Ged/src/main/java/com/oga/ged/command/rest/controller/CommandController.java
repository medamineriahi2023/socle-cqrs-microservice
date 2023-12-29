package com.oga.ged.command.rest.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oga.cqrsref.constants.Constants;
import com.oga.cqrsref.infrastructure.CommandDispatcher;
import com.oga.ged.command.commands.*;
import com.oga.ged.command.handlers.ICommandHandler;
import com.oga.ged.command.rest.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@RestController
@RequestMapping(path = "/ged")
@Slf4j
public class CommandController {
    private static String activeProfile = "default";
    private final CommandDispatcher commandDispatcher;
    private ICommandHandler folderCommandHandler;

    public CommandController(CommandDispatcher commandDispatcher, ICommandHandler folderCommandHandler) {
        this.commandDispatcher = commandDispatcher;
        this.folderCommandHandler = folderCommandHandler;
    }

    /**
     * Create a new folder.
     *
     * @param command The create folder command.
     * @return ResponseEntity with the create folder response.
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(path = "/createfolder")
    public ResponseEntity<BaseResponse> createFolder(@RequestBody CreateFolderCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        commandDispatcher.send(command);
        return new ResponseEntity<>(new CreateResponse(Constants.CREATION_SUCCESS_MESSAGE), HttpStatus.CREATED);
    }

    /**
     * Update an existing folder.
     *
     * @param command The update folder command.
     * @return ResponseEntity with the update folder response.
     */
    @Secured("ROLE_ADMIN")
    @PutMapping(path = "/updatefolder")
    public ResponseEntity<BaseResponse> updateFolder(@RequestBody UpdateFolderCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        commandDispatcher.send(command);
        return new ResponseEntity<>(new UpdateResponse(Constants.UPDATE_SUCCESS_MESSAGE), HttpStatus.CREATED);
    }

    /**
     * Delete an existing folder.
     *
     * @param command The delete folder command.
     * @return ResponseEntity with the delete folder response.
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping(path = "/deletefolder")
    public ResponseEntity<BaseResponse> deleteFolder(@RequestBody DeleteFolderCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        commandDispatcher.send(command);
        return new ResponseEntity<>(new DeleteResponse(Constants.DELETION_SUCCESS_MESSAGE), HttpStatus.CREATED);
    }

    /**
     * Move a folder to a new parent folder.
     *
     * @param command the move folder command
     * @return ResponseEntity with the move folder response.
     */
    @Secured("ROLE_ADMIN")
    @PutMapping("/movefolder")
    public ResponseEntity<BaseResponse> moveFolder(@RequestBody MoveFolderCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        commandDispatcher.send(command);
        return new ResponseEntity<>(new MoveResponse(Constants.ENTITY_MOVE_SUCCESS_MESSAGE_FORMAT), HttpStatus.CREATED);
    }

    /**
     * Create a new file.
     *
     * @param file and request.
     * @return ResponseEntity with the create file response.
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/createfile", consumes = { "multipart/form-data" })
    public ResponseEntity<BaseResponse> createFile(@RequestParam("file") MultipartFile file, @RequestParam("request") String request) throws IOException {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();
        CreateFileCommand convertedObject = gson.fromJson(request, CreateFileCommand.class);
        CreateFileCommand command=new CreateFileCommand();
        var id = UUID.randomUUID().toString();
        command.setId(id);
        command.setFolderId(convertedObject.getFolderId());
        String originalFileName = file.getOriginalFilename();
        command.setName(originalFileName);
        command.setFile(file);
        command.setNodeType(convertedObject.getNodeType());
        command.setRenditions(convertedObject.getRenditions());
        command.setOverwrite(convertedObject.getOverwrite());
        command.setMajorVersion(convertedObject.getMajorVersion());
        command.setComment(convertedObject.getComment());
        command.setAutoRename(convertedObject.getAutoRename());
        command.setRelativePath(convertedObject.getRelativePath());
        command.setVersioningEnabled(convertedObject.getVersioningEnabled());
        commandDispatcher.send(command);
        return new ResponseEntity<>(new CreateResponse(Constants.CREATION_SUCCESS_MESSAGE), HttpStatus.CREATED);
    }

    /**
     * Update an existing file.
     *
     * @param command The update file command.
     * @return ResponseEntity with the update file response.
     */
    @Secured("ROLE_ADMIN")
    @PutMapping(path = "/updatefile")
    public ResponseEntity<BaseResponse> updateFile(@RequestBody UpdateFileCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        commandDispatcher.send(command);
        return new ResponseEntity<>(new UpdateResponse(Constants.UPDATE_SUCCESS_MESSAGE), HttpStatus.CREATED);
    }

    /**
     * Delete an existing file.
     *
     * @param command The delete file command.
     * @return ResponseEntity with the delete file response.
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping(path = "/deletefile")
    public ResponseEntity<BaseResponse> deleteFile(@RequestBody DeleteFileCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        commandDispatcher.send(command);
        return new ResponseEntity<>(new DeleteResponse(Constants.DELETION_SUCCESS_MESSAGE), HttpStatus.CREATED);
    }

    /**
     * Move a file to a new parent folder.
     *
     * @param command the move file command
     * @return ResponseEntity with the move file response.
     */
    @Secured("ROLE_ADMIN")
    @PutMapping("/movefile")
    public ResponseEntity<BaseResponse> moveFile(@RequestBody MoveFileCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        commandDispatcher.send(command);
        return new ResponseEntity<>(new MoveResponse(Constants.ENTITY_MOVE_SUCCESS_MESSAGE_FORMAT), HttpStatus.CREATED);
    }

    /**
     * Create a new user.
     *
     * @param command The create user command.
     * @return ResponseEntity with the create user response.
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(path = "/createuser")
    public ResponseEntity<BaseResponse> createUser(@RequestBody CreateUserCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        commandDispatcher.send(command);
        return new ResponseEntity<>(new CreateResponse(Constants.CREATION_SUCCESS_MESSAGE), HttpStatus.CREATED);
    }

    /**
     * Update an existing user.
     *
     * @param command The update user command.
     * @return ResponseEntity with the update user response.
     */
    @Secured("ROLE_ADMIN")
    @PutMapping(path = "/updateuser")
    public ResponseEntity<BaseResponse> updateUser(@RequestBody UpdateUserCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        commandDispatcher.send(command);
        return new ResponseEntity<>(new UpdateResponse(Constants.UPDATE_SUCCESS_MESSAGE), HttpStatus.CREATED);
    }

    /**
     * Creates a new group.
     *
     * @param command The create group command.
     * @return ResponseEntity with the create group response.
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(path = "/creategroup")
    public ResponseEntity<BaseResponse> createGroup(@RequestBody CreateGroupCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        commandDispatcher.send(command);
        return new ResponseEntity<>(new CreateResponse(Constants.CREATION_SUCCESS_MESSAGE), HttpStatus.CREATED);
    }

    /**
     * Deletes a group.
     *
     * @param command The delete group command.
     * @return ResponseEntity with the delete group response.
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping(path = "/deletegroup")
    public ResponseEntity<BaseResponse> deleteGroup(@RequestBody DeleteGroupCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        commandDispatcher.send(command);
        return new ResponseEntity<>(new CreateResponse(Constants.DELETION_SUCCESS_MESSAGE), HttpStatus.CREATED);
    }

    /**
     * Assigns a person to a group.
     *
     * @param command The assign person to group command.
     * @return ResponseEntity with the assign person to group response.
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(path = "/assignpersontogroup")
    public ResponseEntity<BaseResponse> assignPersonToGroup(@RequestBody AssignPersonToGroupCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        commandDispatcher.send(command);
        return new ResponseEntity<>(new CreateResponse(Constants.CREATION_SUCCESS_MESSAGE), HttpStatus.CREATED);
    }

    /**
     * Removes a person from a group.
     *
     * @param command The remove person from group command.
     * @return ResponseEntity with the remove person from group response.
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping(path = "/removepersonfromgroup")
    public ResponseEntity<BaseResponse> removePersonFromGroup(@RequestBody RemovePersonFromGroupCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        commandDispatcher.send(command);
        return new ResponseEntity<>(new DeleteResponse(Constants.DELETION_SUCCESS_MESSAGE), HttpStatus.CREATED);
    }

    /**
     * Creates a new sharedLink.
     *
     * @param command The create sharedLink command.
     * @return ResponseEntity with the create sharedLink response.
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(path = "/createsharedlink")
    public ResponseEntity<BaseResponse> createSharedLink(@RequestBody CreateSharedLinkCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        commandDispatcher.send(command);
        return new ResponseEntity<>(new CreateResponse(Constants.CREATION_SUCCESS_MESSAGE), HttpStatus.CREATED);
    }

    /**
     * Deletes a sharedLink.
     *
     * @param command The delete sharedLink command.
     * @return ResponseEntity with the delete group response.
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping(path = "/deletesharedlink")
    public ResponseEntity<BaseResponse> deleteSharedLink(@RequestBody DeleteSharedLinkCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        commandDispatcher.send(command);
        return new ResponseEntity<>(new CreateResponse(Constants.DELETION_SUCCESS_MESSAGE), HttpStatus.CREATED);
    }
}
