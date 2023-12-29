package com.oga.ged.command.commands;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a command for assigning person to a group.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignPersonToGroupCommand extends BaseCommand {
    private String groupMembershipId;
    private String groupId;
    private String personId;
}