package com.oga.ged.query.handlers;

import com.oga.ged.query.queries.*;
import com.oga.ged.query.rest.dto.File;
import com.oga.ged.query.rest.dto.Folder;
import com.oga.ged.query.rest.dto.Group;
import com.oga.ged.query.rest.dto.User;

import java.util.List;

/**
 * Handles queries.
 */
public interface QueryHandler {

    /**
     * Handles the query to find folders by parent.
     *
     * @param query The FindFoldersByParent query to handle.
     * @return The list of folders that match the query.
     */
    List<Folder> handle(FindFoldersByParentQuery query);

    /**
     * Handles the query to find folders by id.
     *
     * @param query The FindFolderById query to handle.
     * @return The folder that match the query.
     */
    Folder handle(FindFolderByIdQuery query);

    /**
     * Handles the query to find files by id.
     *
     * @param query The FindFileById query to handle.
     * @return The file that match the query.
     */
    File handle(FindFileByIdQuery query);

    /**
     * Handles the query to search folders by metadata.
     *
     * @param query The SearchFoldersByMetadata query to handle.
     * @return The list of folders that match the query.
     */
    List<Folder> handle(SearchFoldersByMetadataQuery query);

    /**
     * Handles the query to search files by metadata.
     *
     * @param query The SearchFilesByMetadata query to handle.
     * @return The list of files that match the query.
     */
    List<File> handle(SearchFilesByMetadataQuery query);

    /**
     * Handles the query to find users by id.
     *
     * @param query The FindUserById query to handle.
     * @return The user that match the query.
     */
    User handle(FindUserByIdQuery query);

    /**
     * Handles the query to find all users.
     *
     * @param query The FindAllUsers query to handle.
     * @return all users.
     */
    List<User> handle(FindAllUsersQuery query);

    /**
     * Handles the query to find files by folder.
     *
     * @param query The FindFilesByFolder query to handle.
     * @return The list of files that match the query.
     */
    List<File> handle(FindFilesByFolderQuery query);

    /**
     * Handles the query to find all folders.
     *
     * @param query The FindAllFolders query to handle.
     * @return all folders.
     */
    List<Folder> handle(FindAllFoldersQuery query);

    /**
     * Handles the query to count files by folder.
     *
     * @param query The CountFilesByFolder query to handle.
     * @return The count of files based on the folder ID.
     */
    long handle(CountFilesByFolderQuery query);

    /**
     * Handles the query to find a group by its ID.
     *
     * @param query The FindGroupByIdQuery to handle.
     * @return The group matching the given ID.
     */
    Group handle(FindGroupByIdQuery query);

    /**
     * Handles the query to retrieve the members of a group.
     *
     * @param query The ListGroupMembersQuery to handle.
     * @return The list of users who are members of the group.
     */
    List<User> handle(ListGroupMembersQuery query);

    /**
     * Handles the query to retrieve the groups associated with a person.
     *
     * @param query The ListGroupsForPersonQuery to handle.
     * @return The list of groups associated with the given person.
     */
    List<Group> handle(ListGroupsForPersonQuery query);
}
