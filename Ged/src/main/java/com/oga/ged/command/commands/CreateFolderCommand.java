package com.oga.ged.command.commands;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alfresco.core.model.*;

import java.util.Date;
import java.util.List;

/**
 * Represents a command for creating a folder.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateFolderCommand extends BaseCommand {
    private String folderId;
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
    private List<PermissionElement> permissionElements;
    private Definition definition;
    private String relativePath;
    private NodeBodyCreateAssociation association;
    private List<ChildAssociationBody> secondaryChildren;
    private List<AssociationBody> targets;
}
