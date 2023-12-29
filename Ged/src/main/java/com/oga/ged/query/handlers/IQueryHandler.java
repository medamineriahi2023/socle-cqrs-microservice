package com.oga.ged.query.handlers;

import com.oga.ged.query.queries.*;
import com.oga.ged.query.rest.dto.*;
import com.oga.ged.query.rest.repository.*;
import com.oga.ged.query.rest.utils.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the QueryHandler interface.
 */
@Service
@Slf4j
public class IQueryHandler implements QueryHandler {
    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private PersonGroupAssignmentRepository personGroupAssignmentRepository;
    private final MongoTemplate mongoTemplate;

    public IQueryHandler(FolderRepository folderRepository, FileRepository fileRepository, UserRepository userRepository, GroupRepository groupRepository, PersonGroupAssignmentRepository personGroupAssignmentRepository, MongoTemplate mongoTemplate) {
        this.folderRepository = folderRepository;
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.personGroupAssignmentRepository = personGroupAssignmentRepository;
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Handles the query to find folders by parent.
     *
     * @param query The FindFoldersByParent query to handle.
     * @return The list of folders that match the query.
     */
    @Override
    public List<Folder> handle(FindFoldersByParentQuery query) {
        String parentId = query.getParentId();
        return folderRepository.findByParentId(parentId);
    }

    /**
     * Handles the query to find folders by id.
     *
     * @param query The FindFolderById query to handle.
     * @return The folder that match the query.
     */
    @Override
    public Folder handle(FindFolderByIdQuery query) {
        String folderId = query.getFolderId();
        return folderRepository.findById(folderId).orElseThrow(() -> new EntityNotFoundException("Folder with id: " + folderId + " not found"));
    }

    /**
     * Handles the query to find files by id.
     *
     * @param query The FindFileById query to handle.
     * @return The file that match the query.
     */
    @Override
    public File handle(FindFileByIdQuery query) {
        String fileId = query.getFileId();
        return fileRepository.findById(fileId).orElseThrow(() -> new EntityNotFoundException("File with id: " + fileId + " not found"));
    }

    /**
     * Handles the query to search folders by metadata.
     *
     * @param query The SearchFoldersByMetadataQuery query to handle.
     * @return The list of folders that match the query.
     */
    public List<Folder> handle(SearchFoldersByMetadataQuery query) {
        List<Criteria> criteriaList = new ArrayList<>();

        if (query.getName() != null && !query.getName().isEmpty()) {
            criteriaList.add(Criteria.where("name").regex(query.getName(), "i"));
        }

        if (query.getTitle() != null && !query.getTitle().isEmpty()) {
            criteriaList.add(Criteria.where("properties.cm:title").regex(query.getTitle(), "i"));
        }

        if (query.getDescription() != null && !query.getDescription().isEmpty()) {
            criteriaList.add(Criteria.where("properties.cm:description").regex(query.getDescription(), "i"));
        }

        if (query.getCreated() != null && !query.getCreated().isEmpty()) {
            LocalDate targetDate = LocalDate.parse(query.getCreated());
            Date startDate = Date.from(targetDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(targetDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
            criteriaList.add(Criteria.where("created").gte(startDate).lt(endDate));
        }

        if (query.getCreator() != null && !query.getCreator().isEmpty()) {
            criteriaList.add(Criteria.where("creator").is(query.getCreator()));
        }

        if (query.getModified() != null && !query.getModified().isEmpty()) {
            LocalDate targetDate = LocalDate.parse(query.getModified());
            Date startDate = Date.from(targetDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(targetDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
            criteriaList.add(Criteria.where("modified").gte(startDate).lt(endDate));
        }

        if (query.getModifier() != null && !query.getModifier().isEmpty()) {
            criteriaList.add(Criteria.where("modifier").is(query.getModifier()));
        }

        Criteria criteria = new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));
        Query mongoQuery = new Query(criteria);
        return mongoTemplate.find(mongoQuery, Folder.class);
    }

    /**
     * Handles the query to search files by metadata.
     *
     * @param query The SearchFilesByMetadataQuery query to handle.
     * @return The list of files that match the query.
     */
    public List<File> handle(SearchFilesByMetadataQuery query) {
        List<Criteria> criteriaList = new ArrayList<>();

        if (query.getName() != null && !query.getName().isEmpty()) {
            criteriaList.add(Criteria.where("name").regex(query.getName(), "i"));
        }

        if (query.getTitle() != null && !query.getTitle().isEmpty()) {
            criteriaList.add(Criteria.where("properties.cm:title").regex(query.getTitle(), "i"));
        }

        if (query.getDescription() != null && !query.getDescription().isEmpty()) {
            criteriaList.add(Criteria.where("properties.cm:description").regex(query.getDescription(), "i"));
        }

        if (query.getCreated() != null && !query.getCreated().isEmpty()) {
            LocalDate targetDate = LocalDate.parse(query.getCreated());
            Date startDate = Date.from(targetDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(targetDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
            criteriaList.add(Criteria.where("created").gte(startDate).lt(endDate));
        }

        if (query.getCreator() != null && !query.getCreator().isEmpty()) {
            criteriaList.add(Criteria.where("creator").is(query.getCreator()));
        }

        if (query.getModified() != null && !query.getModified().isEmpty()) {
            LocalDate targetDate = LocalDate.parse(query.getModified());
            Date startDate = Date.from(targetDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(targetDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
            criteriaList.add(Criteria.where("modified").gte(startDate).lt(endDate));
        }

        if (query.getModifier() != null && !query.getModifier().isEmpty()) {
            criteriaList.add(Criteria.where("modifier").is(query.getModifier()));
        }

        if (query.getMimeType() != null && !query.getMimeType().isEmpty()) {
            criteriaList.add(Criteria.where("content.mimeType").regex(query.getMimeType(), "i"));
        }

        if (query.getMimeTypeName() != null && !query.getMimeTypeName().isEmpty()) {
            criteriaList.add(Criteria.where("content.mimeTypeName").regex(query.getMimeTypeName(), "i"));
        }

        if (query.getSizeInBytes() != null) {
            criteriaList.add(Criteria.where("content.sizeInBytes").is(query.getSizeInBytes()));
        }

        Criteria criteria = new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));
        Query mongoQuery = new Query(criteria);

        return mongoTemplate.find(mongoQuery, File.class);
    }

    /**
     * Handles the query to find users by id.
     *
     * @param query The FindUserById query to handle.
     * @return The user that match the query.
     */
    @Override
    public User handle(FindUserByIdQuery query) {
        String userId = query.getUserId();
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id: " + userId + " not found"));
    }

    /**
     * Handles the query to find all users.
     *
     * @param query The FindAllUsers query to handle.
     * @return all users.
     */
    @Override
    public List<User> handle(FindAllUsersQuery query) {
        List<User> users = userRepository.findAll();
        return users;
    }

    /**
     * Handles the query to find files by folder.
     *
     * @param query The FindFilesByFolderQuery query to handle.
     * @return The list of files that match the query.
     */
    @Override
    public List<File> handle(FindFilesByFolderQuery query) {
        String folderId = query.getFolderId();
        return fileRepository.findByFolderId(folderId);
    }

    /**
     * Handles the query to find all folders.
     *
     * @param query The FindAllFolders query to handle.
     * @return all folders.
     */
    @Override
    public List<Folder> handle(FindAllFoldersQuery query) {
        List<Folder> folders = folderRepository.findAll();
        return folders;
    }

    /**
     * Handles the query to count files by folder.
     *
     * @param query The CountFilesByFolder query to handle.
     * @return The count of files based on the folder ID.
     */
    @Override
    public long handle(CountFilesByFolderQuery query) {
        long fileCount = fileRepository.countByFolderId(query.getFolderId());
        return fileCount;
    }

    /**
     * Handles the query to find a group by its ID.
     *
     * @param query The FindGroupByIdQuery to handle.
     * @return The group matching the given ID.
     */
    @Override
    public Group handle(FindGroupByIdQuery query) {
        String groupId = query.getGroupId();
        return groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("Group with id: " + groupId + " not found"));
    }

    /**
     * Handles the query to retrieve the members of a group.
     *
     * @param query The ListGroupMembersQuery to handle.
     * @return The list of users who are members of the group.
     */
    @Override
    public List<User> handle(ListGroupMembersQuery query) {
        Optional<Group> optionalGroup = groupRepository.findById(query.getGroupId());

        if (optionalGroup.isEmpty()) {
            throw new EntityNotFoundException("Groupe introuvable : " + query.getGroupId());
        }

        List<PersonGroupAssignment> personGroupAssignments = personGroupAssignmentRepository.findByGroupId(query.getGroupId());

        List<User> groupMembers = personGroupAssignments.stream()
                .map(PersonGroupAssignment::getPersonId)
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        return groupMembers;
    }

    /**
     * Handles the query to retrieve the groups associated with a person.
     *
     * @param query The ListGroupsForPersonQuery to handle.
     * @return The list of groups associated with the given person.
     */
    @Override
    public List<Group> handle(ListGroupsForPersonQuery query) {
        Optional<User> optionalUser = userRepository.findById(query.getPersonId());

        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("Utilisateur introuvable : " + query.getPersonId());
        }
        List<PersonGroupAssignment> personGroupAssignments = personGroupAssignmentRepository.findByPersonId(query.getPersonId());

        List<Group> groups = personGroupAssignments.stream()
                .map(PersonGroupAssignment::getGroupId)
                .map(groupRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        return groups;
    }

}
