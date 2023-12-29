package com.oga.ged.command.events;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.alfresco.core.model.ContentInfo;
import org.alfresco.core.model.PathInfo;
import org.alfresco.core.model.UserInfo;

import java.util.Date;
import java.util.List;

/**
 * Represents a group created sharedLink.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class SharedLinkCreatedEvent extends BaseEvent {
    private String sharedLinkId;
    private String identifier;
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
