package com.oga.ged.query.rest.dto;

import com.oga.cqrsref.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alfresco.core.model.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * Represents a folder entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "folder")
public class Folder extends BaseEntity {
    @Id
    private String id;
    private String parentId;
    private String name;
    private String nodeType;
    private Date created;
    private String creator;
    private Date modified;
    private String modifier;
    private List<String> aspectNames;
    private Object properties;
    private PermissionsBody permissions;
    private Definition definition;
    private String relativePath;
    private NodeBodyCreateAssociation association;
    private List<ChildAssociationBody> secondaryChildren;
    private List<AssociationBody> targets;
}
