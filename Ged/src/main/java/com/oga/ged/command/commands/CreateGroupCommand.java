package com.oga.ged.command.commands;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a command for creating a group.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateGroupCommand extends BaseCommand {
    private String groupId;
    private String displayName;
    private Boolean isRoot;
    private List<String> parentIds;
    private List<String> zones;
}


