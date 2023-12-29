package com.oga.ged.command.domain;

import com.oga.cqrsref.domain.AggregateRoot;
import com.oga.ged.command.commands.*;
import com.oga.ged.command.events.*;
import com.oga.ged.command.rest.utils.exceptions.FileProcessingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Represents an aggregate in the domain model for managing files.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileAggregate extends AggregateRoot {
    private String  id;

    public FileAggregate(CreateFileCommand command) {
        try {
            MultipartFile file = command.getFile();
            byte[] fileData = file.getBytes();
            Binary binary = new Binary(fileData);
            raiseEvent(FileCreatedEvent.builder()
                    .identifier(command.getId())
                    .fileId(command.getFileId())
                    .name(command.getName())
                    .file(binary)
                    .folderId(command.getFolderId())
                    .nodeType(command.getNodeType())
                    .created(command.getCreated())
                    .creator(command.getCreator())
                    .modified(command.getModified())
                    .modifier(command.getModifier())
                    .aspectNames(command.getAspectNames())
                    .properties(command.getProperties())
                    .content(command.getContent())
                    .renditions(command.getRenditions())
                    .overwrite(command.getOverwrite())
                    .majorVersion(command.getMajorVersion())
                    .comment(command.getComment())
                    .autoRename(command.getAutoRename())
                    .relativePath(command.getRelativePath())
                    .versioningEnabled(command.getVersioningEnabled())
                    .build());
        } catch (IOException e) {
            throw new FileProcessingException("Erreur lors du traitement du fichier.", e);
        }
    }
    public void apply(FileCreatedEvent event)
    {
        this.id=event.getIdentifier();
    }

    public FileAggregate(UpdateFileCommand command) {
        raiseEvent(FileUpdatedEvent.builder()
                .identifier(command.getId())
                .fileId(command.getFileId())
                .name(command.getName())
                .title(command.getTitle())
                .description(command.getDescription())
                .modified(command.getModified())
                .modifier(command.getModifier())
                .aspectNames(command.getAspectNames())
                .properties(command.getProperties())
                .permissions(command.getPermissions())
                .build());
    }
    public void apply(FileUpdatedEvent event) {this.id=event.getIdentifier(); }

    public FileAggregate(DeleteFileCommand command) {
        raiseEvent(FileDeletedEvent.builder()
                .fileId(command.getFileId())
                .identifier(command.getId())
                .build());
    }
    public void apply(FileDeletedEvent event)
    {
        this.id=event.getIdentifier();
    }

    public FileAggregate(MoveFileCommand command) {
        raiseEvent(FileMovedEvent.builder()
                .identifier(command.getId())
                .fileId(command.getFileId())
                .targetParentId(command.getTargetParentId())
                .build());
    }

    public void apply(FileMovedEvent event)
    {
        this.id=event.getIdentifier();
    }
}
