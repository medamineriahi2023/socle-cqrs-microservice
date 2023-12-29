package com.oga.ged.command.events;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrsref.events.BaseEvent;
import com.oga.ged.command.rest.utils.BinaryDeserializer;
import com.oga.ged.command.rest.utils.BinarySerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.alfresco.core.model.ContentInfo;
import org.bson.types.Binary;

import java.util.Date;
import java.util.List;

/**
 * Represents a file created event.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class FileCreatedEvent extends BaseEvent {
    private String fileId;
    private String identifier;
    private String name;
    @JsonSerialize(using = BinarySerializer.class)
    @JsonDeserialize(using = BinaryDeserializer.class)
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
    private List<String> renditions;
    private Boolean overwrite;
    private Boolean majorVersion;
    private String comment;
    private Boolean autoRename;
    private String relativePath;
    private Boolean versioningEnabled;


}
