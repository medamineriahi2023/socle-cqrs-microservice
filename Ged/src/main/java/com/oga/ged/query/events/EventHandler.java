package com.oga.ged.query.events;

import com.oga.ged.command.events.*;
import org.springframework.stereotype.Service;

/**
 * Handles events.
 */
@Service
public interface EventHandler {

    /**
     * Handles the event when a folder is created.
     *
     * @param event The FolderCreatedEvent to handle.
     */
    void on(FolderCreatedEvent event);

    /**
     * Handles the event when a folder is updated.
     *
     * @param event The FolderUpdatedEvent to handle.
     */
    void on(FolderUpdatedEvent event);

    /**
     * Handles the event when a folder is deleted.
     *
     * @param event The FolderDeletedEvent to handle.
     */
    void on(FolderDeletedEvent event);

    /**
     * Handles the event when a folder is moved.
     *
     * @param event The FolderMovedEvent to handle.
     */
    void on(FolderMovedEvent event);

    /**
     * Handles the event when a file is created.
     *
     * @param event The FileCreatedEvent to handle.
     */
    void on(FileCreatedEvent event);

    /**
     * Handles the event when a file is updated.
     *
     * @param event The FileUpdatedEvent to handle.
     */
    void on(FileUpdatedEvent event);

    /**
     * Handles the event when a file is deleted.
     *
     * @param event The FileDeletedEvent to handle.
     */
    void on(FileDeletedEvent event);

    /**
     * Handles the event when a file is moved.
     *
     * @param event The FileMovedEvent to handle.
     */
    void on(FileMovedEvent event);

    /**
     * Handles the event when a user is created.
     *
     * @param event The UserCreatedEvent to handle.
     */
    void on(UserCreatedEvent event);

    /**
     * Handles the event when a user is updated.
     *
     * @param event The UserUpdatedEvent to handle.
     */
    void on(UserUpdatedEvent event);

    /**
     * Handles the event when a group is created.
     *
     * @param event The GroupCreatedEvent to handle.
     */
    void on(GroupCreatedEvent event);

    /**
     * Handles the event when a group is deleted.
     *
     * @param event The GroupDeletedEvent to handle.
     */
    void on(GroupDeletedEvent event);

    /**
     * Handles the event when a person is assigned to a group.
     *
     * @param event The PersonAssignedToGroupEvent to handle.
     */
    void on(PersonAssignedToGroupEvent event);

    /**
     * Handles the event when a person is removed from a group.
     *
     * @param event The PersonRemovedFromGroupEvent to handle.
     */
    void on(PersonRemovedFromGroupEvent event);

    /**
     * Handles the event when a sharedLink is created.
     *
     * @param event The SharedLinkCreatedEvent to handle.
     */
    void on(SharedLinkCreatedEvent event);

    /**
     * Handles the event when a sharedLink is deleted.
     *
     * @param event The SharedLinkDeletedEvent to handle.
     */
    void on(SharedLinkDeletedEvent event);


}
