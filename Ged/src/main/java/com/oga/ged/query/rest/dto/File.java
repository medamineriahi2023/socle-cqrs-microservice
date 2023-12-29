package com.oga.ged.query.rest.dto;

import com.oga.cqrsref.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alfresco.core.model.ContentInfo;
import org.alfresco.core.model.PermissionsBody;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * Represents a file entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "file")
public class File extends BaseEntity {
    @Id
    private String id;
    private String name;
    private Binary file;
    private String folderId;
    private String nodeType;
    private List<String> aspectNames;
    private Object properties;
    private Date created;
    private String creator;
    private Date modified;
    private String modifier;
    private ContentInfo content;
    private PermissionsBody permissions;
    private List<String> renditions;
    private Boolean overwrite;
    private Boolean majorVersion;
    private String comment;
    private Boolean autoRename;
    private String relativePath;
    private Boolean versioningEnabled;

}
