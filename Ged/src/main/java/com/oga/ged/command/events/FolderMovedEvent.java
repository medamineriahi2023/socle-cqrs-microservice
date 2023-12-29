package com.oga.ged.command.events;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents the movement of a folder to a new parent folder.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class FolderMovedEvent extends BaseEvent {
    private String folderId;
    private String identifier;
    private String targetParentId;
}
