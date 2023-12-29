package com.oga.ged.command.commands;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alfresco.core.model.ContentInfo;
import org.alfresco.core.model.PathInfo;
import org.alfresco.core.model.UserInfo;

import java.util.Date;
import java.util.List;

/**
 * Represents a command for creating a sharedLink.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSharedLinkCommand extends BaseCommand {
    private String sharedLinkId;
    private String fileId;
    private String name;
    private String title;
    private String description;
    private Date modifiedAt;
    private UserInfo modifiedByUser;
    private UserInfo sharedByUser;
    private ContentInfo content;
    private List<String> allowableOperations;
    private List<String> allowableOperationsOnTarget;
    private Boolean isFavorite;
    private Object properties;
    private List<String> aspectNames;
    private PathInfo path;
}
