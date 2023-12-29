package com.oga.ged.command.commands;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alfresco.core.model.PermissionElement;
import org.alfresco.core.model.PermissionsBody;

import java.util.Date;
import java.util.List;

/**
 * Represents a command for updating a folder.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateFolderCommand extends BaseCommand {
    private String folderId;
    private String name;
    private String title;
    private String description;
    private String nodeType;
    private Date modified;
    private String modifier;
    private List<String> aspectNames;
    private Object properties;
    private PermissionsBody permissions;
    private List<PermissionElement> permissionElements;
}
