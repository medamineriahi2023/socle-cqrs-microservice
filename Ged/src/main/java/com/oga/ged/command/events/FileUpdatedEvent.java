package com.oga.ged.command.events;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.alfresco.core.model.PermissionsBody;

import java.util.Date;
import java.util.List;

/**
 * Represents a file updated event.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class FileUpdatedEvent extends BaseEvent {
    private String fileId;
    private String identifier;
    private String name;
    private String title;
    private String description;
    private String nodeType;
    private Date modified;
    private String modifier;
    private List<String> aspectNames;
    private Object properties;
    private PermissionsBody permissions;
}
