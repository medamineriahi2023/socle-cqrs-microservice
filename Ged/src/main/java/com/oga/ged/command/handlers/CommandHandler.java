package com.oga.ged.command.handlers;

import com.oga.ged.command.commands.*;

/**
 * Handler for commands.
 */
public interface CommandHandler {

    /**
     * Handles the create folder command.
     *
     * @param createFolderCommand the create folder command to handle
     */
    void handle(CreateFolderCommand createFolderCommand);

    /**
     * Handles the update folder command.
     *
     * @param updateFolderCommand the update folder command to handle
     */
    void handle(UpdateFolderCommand updateFolderCommand);

    /**
     * Handles the delete folder command.
     *
     * @param deleteFolderCommand the delete folder command to handle
     */
    void handle(DeleteFolderCommand deleteFolderCommand);

    /**
     * Handles the move folder command.
     *
     * @param moveFolderCommand the move folder command to handle
     */
    void handle(MoveFolderCommand moveFolderCommand);

    /**
     * Handles the create file command.
     *
     * @param createFileCommand the create file command to handle
     */
    void handle(CreateFileCommand createFileCommand);

    /**
     * Handles the update file command.
     *
     * @param updateFileCommand the update file command to handle
     */
    void handle(UpdateFileCommand updateFileCommand);

    /**
     * Handles the delete file command.
     *
     * @param deleteFileCommand the delete file command to handle
     */
    void handle(DeleteFileCommand deleteFileCommand);

    /**
     * Handles the move file command.
     *
     * @param moveFileCommand the move file command to handle
     */
    void handle(MoveFileCommand moveFileCommand);

    /**
     * Handles the create user command.
     *
     * @param createUserCommand the create user command to handle
     */
    void handle(CreateUserCommand createUserCommand);

    /**
     * Handles the update user command.
     *
     * @param updateUserCommand the update user command to handle
     */
    void handle(UpdateUserCommand updateUserCommand);

    /**
     * Handles the create group command.
     *
     * @param createGroupCommand the create group command to handle
     */
    void handle(CreateGroupCommand createGroupCommand);

    /**
     * Handles the delete group command.
     *
     * @param deleteGroupCommand the delete group command to handle
     */
    void handle(DeleteGroupCommand deleteGroupCommand);

    /**
     * Handles the assign person to group command.
     *
     * @param assignPersonToGroupCommand the assign person to group command to handle
     */
    void handle(AssignPersonToGroupCommand assignPersonToGroupCommand);

    /**
     * Handles the remove person from group command.
     *
     * @param removePersonFromGroupCommand the remove person from group command to handle
     */
    void handle(RemovePersonFromGroupCommand removePersonFromGroupCommand);

    /**
     * Handles the delete sharedLink command.
     *
     * @param deleteSharedLinkCommand the delete sharedLink command to handle
     */
    void handle(DeleteSharedLinkCommand deleteSharedLinkCommand);
    }
