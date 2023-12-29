package com.oga.ged.query.rest.controller;

import com.oga.cqrsref.constants.Constants;
import com.oga.cqrsref.infrastructure.IQueryDispatcher;
import com.oga.ged.query.queries.*;
import com.oga.ged.query.rest.dto.File;
import com.oga.ged.query.rest.dto.Folder;
import com.oga.ged.query.rest.dto.Group;
import com.oga.ged.query.rest.dto.User;
import com.oga.ged.query.rest.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/ged")
public class QueryController {
    private final Logger logger = Logger.getLogger(QueryController.class.getName());
    @Autowired
    private IQueryDispatcher queryDispatcher;

    /**
     * Endpoint to retrieve folders by parent ID.
     *
     * @param parentId the parent ID
     * @return ResponseEntity containing the response of the request
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/findFolderByParent/{parentId}")
    public ResponseEntity<FindFolderByParentResponse> getFoldersByParent(@PathVariable String parentId) {
        List<Folder> folders = queryDispatcher.send(new FindFoldersByParentQuery(parentId));
        if (folders == null ) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        var response = FindFolderByParentResponse.builder()
                .folders(folders)
                .message(MessageFormat.format(Constants.FIND_BY_SUCCESS_MESSAGE_FORMAT, folders.size()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve folders by ID.
     *
     * @param folderId the ID
     * @return ResponseEntity containing the response of the request
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/findFolderById/{folderId}")
    public ResponseEntity<FindFolderByIdResponse> getFolderById(@PathVariable String folderId) {
        Folder folder = (Folder) queryDispatcher.sendObject(new FindFolderByIdQuery(folderId));
        if (folder == null ) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        var response = FindFolderByIdResponse.builder()
                .folder(folder)
                .message(MessageFormat.format(Constants.FIND_BY_SUCCESS_MESSAGE_FORMAT, folder.getId()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve files by ID.
     *
     * @param fileId the ID
     * @return ResponseEntity containing the response of the request
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/findFileById/{fileId}")
    public ResponseEntity<FindFileByIdResponse> getFileById(@PathVariable String fileId) {
        File file = (File) queryDispatcher.sendObject(new FindFileByIdQuery(fileId));
        if (file == null ) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        var response = FindFileByIdResponse.builder()
                .file(file)
                .message(MessageFormat.format(Constants.FIND_BY_SUCCESS_MESSAGE_FORMAT, file.getId()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint to search folders by metadata.
     *
     * @param name        Filter by folder name (optional).
     * @param title       Filter by folder title (optional).
     * @param description Filter by folder description (optional).
     * @param created     Filter by folder creation date (optional). Format: "yyyy/MM/dd".
     * @param creator     Filter by folder creator (optional).
     * @param modified    Filter by folder modification date (optional). Format: "yyyy/MM/dd".
     * @param modifier    Filter by folder modifier (optional).
     * @return ResponseEntity containing the response of the request, including a list of matching folders.
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/searchFoldersByMetadata")
    public ResponseEntity<SearchFoldersByMetadataResponse> searchFoldersByMetadata(@RequestParam(required = false) String name,
                                                                                   @RequestParam(required = false) String title,
                                                                                   @RequestParam(required = false) String description,
                                                                                   @RequestParam(required = false) String created,
                                                                                   @RequestParam(required = false) String creator,
                                                                                   @RequestParam(required = false) String modified,
                                                                                   @RequestParam(required = false) String modifier) {
        try {
            String extractedDate = "";
            String extractedDateModif = "";
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");


            if (created != null && !created.isEmpty()) {
                Date date = inputDateFormat.parse(created);
                extractedDate = outputDateFormat.format(date);
            }

            if (modified != null && !modified.isEmpty()) {
                Date dateModif = inputDateFormat.parse(modified);
                extractedDateModif = outputDateFormat.format(dateModif);
            }

            SearchFoldersByMetadataQuery query = new SearchFoldersByMetadataQuery(name, title, description, extractedDate, creator, extractedDateModif, modifier);
            List<Folder> folders = queryDispatcher.send(query);

            if (folders == null || folders.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            var response = SearchFoldersByMetadataResponse.builder()
                    .folders(folders)
                    .message(MessageFormat.format(Constants.FIND_BY_SUCCESS_MESSAGE_FORMAT, folders.size()))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint to search files by metadata.
     *
     * @param name           Filter by file name (optional).
     * @param title          Filter by file title (optional).
     * @param description    Filter by file description (optional).
     * @param created        Filter by file creation date (optional). Format: "yyyy/MM/dd".
     * @param creator        Filter by file creator (optional).
     * @param modified       Filter by file modification date (optional). Format: "yyyy/MM/dd".
     * @param modifier       Filter by file modifier (optional).
     * @param mimeType       Filter by file MIME type (optional).
     * @param mimeTypeName   Filter by file MIME type name (optional).
     * @param sizeInBytes    Filter by file size in bytes (optional).
     * @return ResponseEntity containing the response of the request, including a list of matching files.
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/searchFilesByMetadata")
    public ResponseEntity<SearchFilesByMetadataResponse> searchFilessByMetadata(@RequestParam(required = false) String name,
                                                                                 @RequestParam(required = false) String title,
                                                                                 @RequestParam(required = false) String description,
                                                                                 @RequestParam(required = false) String created,
                                                                                 @RequestParam(required = false) String creator,
                                                                                 @RequestParam(required = false) String modified,
                                                                                 @RequestParam(required = false) String modifier,
                                                                                @RequestParam(required = false) String mimeType,
                                                                                @RequestParam(required = false) String mimeTypeName,
                                                                                @RequestParam(required = false) Long sizeInBytes) {
        try {
            String extractedDate = "";
            String extractedDateModif = "";
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");


            if (created != null && !created.isEmpty()) {
                Date date = inputDateFormat.parse(created);
                extractedDate = outputDateFormat.format(date);
            }

            if (modified != null && !modified.isEmpty()) {
                Date dateModif = inputDateFormat.parse(modified);
                extractedDateModif = outputDateFormat.format(dateModif);
            }

            SearchFilesByMetadataQuery query = new SearchFilesByMetadataQuery(name, title, description, extractedDate, creator, extractedDateModif, modifier,mimeType,mimeTypeName,sizeInBytes);
            List<File> files = queryDispatcher.send(query);

            if (files == null || files.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            var response = SearchFilesByMetadataResponse.builder()
                    .files(files)
                    .message(MessageFormat.format(Constants.FIND_BY_SUCCESS_MESSAGE_FORMAT, files.size()))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint to download a file by its ID.
     *
     * @param fileId the ID of the file to download
     * @return ResponseEntity containing the response of the request with the file content
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        File file = (File) queryDispatcher.sendObject(new FindFileByIdQuery(fileId));
        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayResource resource = new ByteArrayResource(file.getFile().getData());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData(file.getName(), file.getName());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    /**
     * Endpoint to retrieve users by ID.
     *
     * @param userId the ID
     * @return ResponseEntity containing the response of the request
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/findUserById/{userId}")
    public ResponseEntity<FindUserByIdResponse> getUserById(@PathVariable String userId) {
        User user = (User) queryDispatcher.sendObject(new FindUserByIdQuery(userId));
        if (user == null ) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        var response = FindUserByIdResponse.builder()
                .user(user)
                .message(MessageFormat.format(Constants.FIND_BY_SUCCESS_MESSAGE_FORMAT, user.getId()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve all users.
     *
     * @return ResponseEntity containing the response of the request
     */
    @GetMapping(path = "/findAllUsers")
    public ResponseEntity<FindAllUsersResponse> getAllUsers() {
        List<User> users = queryDispatcher.send(new FindAllUsersQuery());
        if (users == null ) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        var response = FindAllUsersResponse.builder()
                .users(users)
                .message(MessageFormat.format(Constants.GET_ALL_SUCCESS_MESSAGE, users.size()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve files by folder ID.
     *
     * @param folderId the folder ID
     * @return ResponseEntity containing the response of the request
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/findFilesByFolder/{folderId}")
    public ResponseEntity<FindFilesByFolderResponse> getFilesByFolder(@PathVariable String folderId) {
        List<File> files = queryDispatcher.send(new FindFilesByFolderQuery(folderId));
        if (files == null ) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        var response = FindFilesByFolderResponse.builder()
                .files(files)
                .message(MessageFormat.format(Constants.FIND_BY_SUCCESS_MESSAGE_FORMAT, files.size()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve all folders.
     *
     * @return ResponseEntity containing the response of the request
     */
    @GetMapping(path = "/findAllFolders")
    public ResponseEntity<FindAllFoldersResponse> getAllFolders() {
        List<Folder> folders = queryDispatcher.send(new FindAllFoldersQuery());
        if (folders == null ) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        var response = FindAllFoldersResponse.builder()
                .folders(folders)
                .message(MessageFormat.format(Constants.GET_ALL_SUCCESS_MESSAGE, folders.size()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint to count files by folder ID.
     *
     * @return ResponseEntity containing the response of the request
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/countFilesByFolder/{folderId}")
    public ResponseEntity<CountFilesByFolderResponse> countFilesByFolder(@PathVariable String folderId) {
        long fileCount = (long) queryDispatcher.sendObject(new CountFilesByFolderQuery(folderId));
        if (fileCount == 0) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        var response = CountFilesByFolderResponse.builder()
                .fileCount(fileCount)
                .message(MessageFormat.format(Constants.COUNT_SUCCESS_MESSAGE_FORMAT, fileCount))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint to find a group by ID.
     *
     * @param groupId The ID of the group to find.
     * @return ResponseEntity containing the response of the request.
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/findGroupById/{groupId}")
    public ResponseEntity<FindGroupByIdResponse> getGroupById(@PathVariable String groupId) {
        Group group = (Group) queryDispatcher.sendObject(new FindGroupByIdQuery(groupId));
        if (group == null ) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        var response = FindGroupByIdResponse.builder()
                .group(group)
                .message(MessageFormat.format(Constants.FIND_BY_SUCCESS_MESSAGE_FORMAT, group.getId()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint to get the members of a group by its ID.
     *
     * @param groupId The ID of the group to get the members for.
     * @return ResponseEntity containing the response of the request.
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(path ="/FindGroupMembers/{groupId}")
    public ResponseEntity<FindGroupMembersResponse> getGroupMembers(@PathVariable String groupId) {
        ListGroupMembersQuery query = new ListGroupMembersQuery(groupId);
        List<User> groupMembers = queryDispatcher.send(query);

        if (groupMembers.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        var response = FindGroupMembersResponse.builder()
                .groupMembers(groupMembers)
                .message(MessageFormat.format(Constants.FIND_BY_SUCCESS_MESSAGE_FORMAT, groupId))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint to find groups associated with a person.
     *
     * @param personId The ID of the person to find groups for.
     * @return ResponseEntity containing the response of the request.
     */
    @GetMapping(path = "/findGroupsForPerson/{personId}")
    public ResponseEntity<FindGroupsForPersonResponse> getGroupsForPerson(@PathVariable String personId) {
        ListGroupsForPersonQuery query = new ListGroupsForPersonQuery(personId);
        List<Group> groups = queryDispatcher.send(query);

        if (groups.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        var response = FindGroupsForPersonResponse.builder()
                .groups(groups)
                .message(MessageFormat.format(Constants.FIND_BY_SUCCESS_MESSAGE_FORMAT, personId))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
