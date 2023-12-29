package com.oga.ged.command.commands;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a command for deleting a file.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteFileCommand extends BaseCommand {
    private String fileId;}
