package com.oga.ged.command.commands;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a command for moving a folder to a new parent folder.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MoveFolderCommand extends BaseCommand {
    private String folderId;
    private String targetParentId;
}
