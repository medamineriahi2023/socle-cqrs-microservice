package com.oga.ged.command.commands;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a command for deleting a group.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteGroupCommand extends BaseCommand {
    private String groupId;

}
