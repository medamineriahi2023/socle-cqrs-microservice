package com.oga.ged.command.events;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.alfresco.core.model.*;

import java.util.Date;
import java.util.List;

/**
 * Represents a folder created event.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class FolderCreatedEvent extends BaseEvent {
    private String folderId;
    private String identifier;
    private String parentId;
    private String name;
    private String title;
    private String description;
    private String nodeType;
    private Date created;
    private String creator;
    private Date modified;
    private String modifier;
    private List<String> aspectNames;
    private Object properties;
    private PermissionsBody permissions;
    private Definition definition;
    private String relativePath;
    private NodeBodyCreateAssociation association;
    private List<ChildAssociationBody> secondaryChildren;
    private List<AssociationBody> targets;
}
