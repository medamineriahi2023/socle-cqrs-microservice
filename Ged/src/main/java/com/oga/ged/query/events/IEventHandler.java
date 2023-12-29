package com.oga.ged.query.events;

import com.oga.cqrsref.constants.Constants;
import com.oga.ged.command.events.*;
import com.oga.ged.query.rest.dto.*;
import com.oga.ged.query.rest.repository.*;
import com.oga.ged.query.rest.utils.exceptions.EntityNotFoundException;
import com.oga.ged.query.rest.utils.exceptions.InvalidIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of EventHandler interface.
 */
@Service
@EnableMongoRepositories("com.oga.ged.query.rest.repository")
@Slf4j
public class IEventHandler implements EventHandler {
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
    @Autowired
    private SharedLinkRepository sharedLinkRepository;

    /**
     * Handles the FolderCreatedEvent.
     *
     * @param event The FolderCreatedEvent to handle.
     */
    @Override
    public void on(FolderCreatedEvent event) {
        Folder Folder =new Folder() ;
        try{
            Folder = Folder.builder()
                    .id(event.getFolderId())
                    .parentId(event.getParentId())
                    .name(event.getName())
                    .nodeType(event.getNodeType())
                    .created(event.getCreated())
                    .creator(event.getCreator())
                    .modified(event.getModified())
                    .modifier(event.getModifier())
                    .aspectNames(event.getAspectNames())
                    .properties(event.getProperties())
                    .permissions(event.getPermissions())
                    .definition(event.getDefinition())
                    .relativePath(event.getRelativePath())
                    .association(event.getAssociation())
                    .secondaryChildren(event.getSecondaryChildren())
                    .targets(event.getTargets())
                    .build();
            folderRepository.save(Folder);
        }catch (NumberFormatException e) {
            throw new InvalidIdException(Constants.INVALID_ID_ERROR_MESSAGE, e);
        }
    }

    /**
     * Handles the FolderUpdatedEvent.
     *
     * @param event The FolderUpdatedEvent to handle.
     */
    @Override
    public void on(FolderUpdatedEvent event) {
        String folderId = event.getFolderId();

        try {
            Optional<Folder> optionalFolder = folderRepository.findById(folderId);
            if (optionalFolder.isPresent()) {
                Folder folder = optionalFolder.get();
                folder.setName(event.getName());
                folder.setModified(event.getModified());
                folder.setModifier(event.getModifier());
                folder.setAspectNames(event.getAspectNames());
                folder.setProperties(event.getProperties());
                folder.setPermissions(event.getPermissions());
                folderRepository.save(folder);
                log.info("Dossier mis à jour avec succès !");
            } else {
                throw new EntityNotFoundException(Constants.ENTITY_NOT_FOUND_ERROR_MESSAGE_FORMAT + folderId);
            }
        } catch (EntityNotFoundException e) {
            log.info(e.getMessage());
            throw e;
        }
    }

    /**
     * Handles the FolderDeletedEvent.
     *
     * @param event The FolderDeletedEvent to handle.
     */
    @Override
    public void on(FolderDeletedEvent event) {
        String folderId = event.getFolderId();
        try {
            Optional<Folder> optionalFolder = folderRepository.findById(folderId);
            if (optionalFolder.isPresent()) {
                Folder folder = optionalFolder.get();
                folderRepository.delete(folder);
                log.info("Dossier supprimé avec succès !");
            } else {
                throw new EntityNotFoundException(Constants.ENTITY_NOT_FOUND_ERROR_MESSAGE_FORMAT + folderId);
            }
        } catch (EntityNotFoundException e) {
            log.info("Erreur lors de la suppression du dossier : " + e.getMessage());
            throw e;
        }
    }

    /**
     * Handles the FolderMovedEvent.
     *
     * @param event The FolderMovedEvent to handle.
     */
    @Override
    public void on(FolderMovedEvent event) {
        String folderId = event.getFolderId();
        try {
        Optional<Folder> optionalFolder = folderRepository.findById(folderId);
        if (optionalFolder.isPresent()) {
            Folder folder = optionalFolder.get();
            folder.setParentId(event.getTargetParentId());
            folderRepository.save(folder);
            log.info("Déplacement du dossier effectué avec succès !");
        } else {
            throw new EntityNotFoundException(Constants.ENTITY_NOT_FOUND_ERROR_MESSAGE_FORMAT + folderId);
        }
        } catch (EntityNotFoundException e) {
            log.info("Erreur lors du déplacement du dossier : " + e.getMessage());
            throw e;
        }
    }

    /**
     * Handles the FileCreatedEvent.
     *
     * @param event The FileCreatedEvent to handle.
     */
    @Override
    public void on(FileCreatedEvent event) {
        File File =new File() ;
        try{
            File = File.builder()
                    .id(event.getFileId())
                    .folderId(event.getFolderId())
                    .name(event.getName())
                    .file(event.getFile())
                    .nodeType(event.getNodeType())
                    .created(event.getCreated())
                    .creator(event.getCreator())
                    .modified(event.getModified())
                    .modifier(event.getModifier())
                    .aspectNames(event.getAspectNames())
                    .properties(event.getProperties())
                    .content(event.getContent())
                    .renditions(event.getRenditions())
                    .overwrite(event.getOverwrite())
                    .majorVersion(event.getMajorVersion())
                    .comment(event.getComment())
                    .autoRename(event.getAutoRename())
                    .relativePath(event.getRelativePath())
                    .versioningEnabled(event.getVersioningEnabled())
                    .build();
            fileRepository.save(File);
        }catch (NumberFormatException e) {
            throw new InvalidIdException(Constants.INVALID_ID_ERROR_MESSAGE, e);
        }
    }

    /**
     * Handles the FileUpdatedEvent.
     *
     * @param event The FileUpdatedEvent to handle.
     */
    @Override
    public void on(FileUpdatedEvent event) {
        String fileId = event.getFileId();

        try {
            Optional<File> optionalFile = fileRepository.findById(fileId);
            if (optionalFile.isPresent()) {
                File file = optionalFile.get();
                file.setName(event.getName());
                file.setModified(event.getModified());
                file.setModifier(event.getModifier());
                file.setAspectNames(event.getAspectNames());
                file.setProperties(event.getProperties());
                file.setPermissions(event.getPermissions());
                fileRepository.save(file);
                log.info("Document mis à jour avec succès !");
            } else {
                throw new EntityNotFoundException(Constants.ENTITY_NOT_FOUND_ERROR_MESSAGE_FORMAT + fileId);
            }
        } catch (EntityNotFoundException e) {
            log.info(e.getMessage());
            throw e;
        }
    }

    /**
     * Handles the FileDeletedEvent.
     *
     * @param event The FileDeletedEvent to handle.
     */
    @Override
    public void on(FileDeletedEvent event) {
        String fileId = event.getFileId();
        try {
            Optional<File> optionalFile = fileRepository.findById(fileId);
            if (optionalFile.isPresent()) {
                File file = optionalFile.get();
                fileRepository.delete(file);
                log.info("Document supprimé avec succès !");
            } else {
                throw new EntityNotFoundException(Constants.ENTITY_NOT_FOUND_ERROR_MESSAGE_FORMAT + fileId);
            }
        } catch (EntityNotFoundException e) {
            log.info("Erreur lors de la suppression du document : " + e.getMessage());
            throw e;
        }
    }

    /**
     * Handles the FileMovedEvent.
     *
     * @param event The FileMovedEvent to handle.
     */
    @Override
    public void on(FileMovedEvent event) {
        String fileId = event.getFileId();
        try {
            Optional<File> optionalFile = fileRepository.findById(fileId);
            if (optionalFile.isPresent()) {
                File file = optionalFile.get();
                file.setFolderId(event.getTargetParentId());
                fileRepository.save(file);
                log.info("Déplacement du document effectué avec succès !");
            } else {
                throw new EntityNotFoundException(Constants.ENTITY_NOT_FOUND_ERROR_MESSAGE_FORMAT + fileId);
            }
        } catch (EntityNotFoundException e) {
            log.info("Erreur lors du déplacement du document : " + e.getMessage());
            throw e;
        }
    }

    /**
     * Handles the UserCreatedEvent.
     *
     * @param event The UserCreatedEvent to handle.
     */
    @Override
    public void on(UserCreatedEvent event) {
        User User =new User() ;
        try{
            User = User.builder()
                    .id(event.getUserId())
                    .password(event.getPassword())
                    .firstName(event.getFirstName())
                    .lastName(event.getLastName())
                    .displayName(event.getDisplayName())
                    .description(event.getDescription())
                    .avatarId(event.getAvatarId())
                    .email(event.getEmail())
                    .skypeId(event.getSkypeId())
                    .googleId(event.getGoogleId())
                    .instantMessageId(event.getInstantMessageId())
                    .jobTitle(event.getJobTitle())
                    .location(event.getLocation())
                    .company(event.getCompany())
                    .mobile(event.getMobile())
                    .telephone(event.getTelephone())
                    .statusUpdatedAt(event.getStatusUpdatedAt())
                    .userStatus(event.getUserStatus())
                    .enabled(event.getEnabled())
                    .emailNotificationsEnabled(event.getEmailNotificationsEnabled())
                    .aspectNames(event.getAspectNames())
                    .properties(event.getProperties())
                    .capabilities(event.getCapabilities())
                    .build();
            userRepository.save(User);
        }catch (NumberFormatException e) {
            throw new InvalidIdException(Constants.INVALID_ID_ERROR_MESSAGE, e);
        }
    }

    /**
     * Handles the event when a user is updated.
     *
     * @param event The UserUpdatedEvent to handle.
     */
    @Override
    public void on(UserUpdatedEvent event) {
        String userId = event.getUserId();

        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setPassword(event.getPassword());
                user.setFirstName(event.getFirstName());
                user.setLastName(event.getLastName());
                user.setDescription(event.getDescription());
                user.setEmail(event.getEmail());
                user.setSkypeId(event.getSkypeId());
                user.setGoogleId(event.getGoogleId());
                user.setInstantMessageId(event.getInstantMessageId());
                user.setJobTitle(event.getJobTitle());
                user.setLocation(event.getLocation());
                user.setCompany(event.getCompany());
                user.setMobile(event.getMobile());
                user.setTelephone(event.getTelephone());
                user.setUserStatus(event.getUserStatus());
                user.setEnabled(event.getEnabled());
                user.setEmailNotificationsEnabled(event.getEmailNotificationsEnabled());
                user.setAspectNames(event.getAspectNames());
                user.setProperties(event.getProperties());
                userRepository.save(user);
                log.info("Utilisateur mis à jour avec succès !");
            } else {
                throw new EntityNotFoundException(Constants.ENTITY_NOT_FOUND_ERROR_MESSAGE_FORMAT + userId);
            }
        } catch (EntityNotFoundException e) {
            log.info(e.getMessage());
            throw e;
        }
    }

    /**
     * Handles the event when a group is created.
     *
     * @param event The GroupCreatedEvent to handle.
     */
    @Override
    public void on(GroupCreatedEvent event) {
        Group Group =new Group() ;
        try{
            Group = Group.builder()
                    .id(event.getGroupId())
                    .displayName(event.getDisplayName())
                    .isRoot(event.getIsRoot())
                    .parentIds(event.getParentIds())
                    .zones(event.getZones())
                    .build();
            groupRepository.save(Group);
        }catch (NumberFormatException e) {
            throw new InvalidIdException(Constants.INVALID_ID_ERROR_MESSAGE, e);
        }
    }

    /**
     * Handles the event when a group is deleted.
     *
     * @param event The GroupDeletedEvent to handle.
     */
    @Override
    public void on(GroupDeletedEvent event) {
        String groupId = event.getGroupId();
        try {
            Optional<Group> optionalGroup = groupRepository.findById(groupId);
            if (optionalGroup.isPresent()) {
                Group group = optionalGroup.get();
                groupRepository.delete(group);
                log.info("Groupe supprimé avec succès !");
            } else {
                throw new EntityNotFoundException(Constants.ENTITY_NOT_FOUND_ERROR_MESSAGE_FORMAT + groupId);
            }
        } catch (EntityNotFoundException e) {
            log.info("Erreur lors de la suppression du groupe : " + e.getMessage());
            throw e;
        }
    }

    /**
     * Handles the event when a person is assigned to a group.
     *
     * @param event The PersonAssignedToGroupEvent to handle.
     */
    @Override
    public void on(PersonAssignedToGroupEvent event) {
        PersonGroupAssignment PersonGroupAssignment =new PersonGroupAssignment() ;
        try{
            PersonGroupAssignment = PersonGroupAssignment.builder()
                    .id(event.getGroupMembershipId())
                    .groupId(event.getGroupId())
                    .personId(event.getPersonId())
                    .build();
            personGroupAssignmentRepository.save(PersonGroupAssignment);
        }catch (NumberFormatException e) {
            throw new InvalidIdException(Constants.INVALID_ID_ERROR_MESSAGE, e);
        }
    }

    /**
     * Handles the event when a person is removed from a group.
     *
     * @param event The PersonRemovedFromGroupEvent to handle.
     */
    @Override
    public void on(PersonRemovedFromGroupEvent event) {
        String groupId = event.getGroupId();
        String personId = event.getPersonId();
        try {
            Optional<PersonGroupAssignment> optionalRes = personGroupAssignmentRepository.findByGroupIdAndPersonId(groupId,personId);
            if (optionalRes.isPresent()) {
                PersonGroupAssignment personGroupAssignment = optionalRes.get();
                personGroupAssignmentRepository.delete(personGroupAssignment);
                log.info("Affectation de la personne au groupe enlevée avec succès !");
            } else {
                throw new EntityNotFoundException(Constants.ENTITY_NOT_FOUND_ERROR_MESSAGE_FORMAT);
            }
        } catch (EntityNotFoundException e) {
            log.info("Erreur lors de l'enlèvement de l'affectation' : " + e.getMessage());
            throw e;
        }
    }

    /**
     * Handles the event when a sharedLink is created.
     *
     * @param event The GroupCreatedEvent to handle.
     */
    @Override
    public void on(SharedLinkCreatedEvent event) {
        SharedLink SharedLink =new SharedLink() ;
        try{
            SharedLink = SharedLink.builder()
                    .id(event.getSharedLinkId())
                    .name(event.getName())
                    .title(event.getTitle())
                    .description(event.getDescription())
                    .modifiedAt(event.getModifiedAt())
                    .modifiedByUser(event.getModifiedByUser())
                    .sharedByUser(event.getSharedByUser())
                    .content(event.getContent())
                    .allowableOperations(event.getAllowableOperations())
                    .allowableOperationsOnTarget(event.getAllowableOperationsOnTarget())
                    .isFavorite(event.getIsFavorite())
                    .properties(event.getProperties())
                    .aspectNames(event.getAspectNames())
                    .path(event.getPath())
                    .build();
            sharedLinkRepository.save(SharedLink);
        }catch (NumberFormatException e) {
            throw new InvalidIdException(Constants.INVALID_ID_ERROR_MESSAGE, e);
        }
    }

    /**
     * Handles the event when a sharedLink is deleted.
     *
     * @param event The SharedLinkDeletedEvent to handle.
     */
    @Override
    public void on(SharedLinkDeletedEvent event) {
        String sharedLinkId = event.getSharedLinkId();
        try {
            Optional<SharedLink> optionalSharedLink = sharedLinkRepository.findById(sharedLinkId);
            if (optionalSharedLink.isPresent()) {
                SharedLink sharedLink = optionalSharedLink.get();
                sharedLinkRepository.delete(sharedLink);
                log.info("lien partagé supprimé avec succès !");
            } else {
                throw new EntityNotFoundException(Constants.ENTITY_NOT_FOUND_ERROR_MESSAGE_FORMAT + sharedLinkId);
            }
        } catch (EntityNotFoundException e) {
            log.info("Erreur lors de la suppression du lien partagé : " + e.getMessage());
            throw e;
        }
    }
}
