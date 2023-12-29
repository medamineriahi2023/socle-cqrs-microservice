package com.oga.ged.command.commands;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a command for removing a person from a group.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RemovePersonFromGroupCommand extends BaseCommand {
    private String groupId;
    private String personId;
}
