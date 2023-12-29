package com.oga.ged.command.events;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Represents a group created event.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class GroupCreatedEvent  extends BaseEvent {
    private String groupId;
    private String identifier;
    private String displayName;
    private Boolean isRoot;
    private List<String> parentIds;
    private List<String> zones;
}
