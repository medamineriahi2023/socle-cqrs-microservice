package com.oga.ged.command.domain;

import com.oga.cqrsref.domain.AggregateRoot;
import com.oga.ged.command.commands.*;
import com.oga.ged.command.events.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an aggregate in the domain model for managing folders.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FolderAggregate extends AggregateRoot {
    private String  id;
    public FolderAggregate(CreateFolderCommand command) {
        raiseEvent(FolderCreatedEvent.builder()
                .folderId(command.getFolderId())
                .identifier(command.getId())
                .parentId(command.getParentId())
                .name(command.getName())
                .nodeType(command.getNodeType())
                .created(command.getCreated())
                .creator(command.getCreator())
                .modified(command.getModified())
                .modifier(command.getModifier())
                .aspectNames(command.getAspectNames())
                .properties(command.getProperties())
                .permissions(command.getPermissions())
                .definition(command.getDefinition())
                .relativePath(command.getRelativePath())
                .association(command.getAssociation())
                .secondaryChildren(command.getSecondaryChildren())
                .targets(command.getTargets())
                .build());
    }

    public void apply (FolderCreatedEvent event)
    {
        this.id=event.getIdentifier();

    }

    public FolderAggregate(UpdateFolderCommand command) {
        raiseEvent(FolderUpdatedEvent.builder()
                .identifier(command.getId())
                .folderId(command.getFolderId())
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

    public void apply(FolderUpdatedEvent event)
    {
        this.id=event.getIdentifier();
    }

    public FolderAggregate(DeleteFolderCommand command) {
        raiseEvent(FolderDeletedEvent.builder()
                .folderId(command.getFolderId())
                .identifier(command.getId())
                .build());
    }

    public void apply(FolderDeletedEvent event)
    {
        this.id=event.getIdentifier();
    }

    public FolderAggregate(MoveFolderCommand command) {
        raiseEvent(FolderMovedEvent.builder()
                .identifier(command.getId())
                .folderId(command.getFolderId())
                .targetParentId(command.getTargetParentId())
                .build());
    }

    public void apply(FolderMovedEvent event)
    {
        this.id=event.getIdentifier();
    }

}
