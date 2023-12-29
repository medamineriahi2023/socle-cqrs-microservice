package com.oga.ged.command.commands;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alfresco.core.model.ContentInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * Represents a command for creating a file.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateFileCommand extends BaseCommand {
    private String fileId;
    private String name;
    private MultipartFile file;
    private String folderId;
    private String nodeType;
    private List<String> aspectNames;
    private Object properties;
    private Date created;
    private String creator;
    private Date modified;
    private String modifier;
    private ContentInfo content;
    private List<String> renditions;
    private Boolean overwrite;
    private Boolean majorVersion;
    private String comment;
    private Boolean autoRename;
    private String relativePath;
    private Boolean versioningEnabled;

}
