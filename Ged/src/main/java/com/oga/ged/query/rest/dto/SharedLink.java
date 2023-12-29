package com.oga.ged.query.rest.dto;

import com.oga.cqrsref.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alfresco.core.model.ContentInfo;
import org.alfresco.core.model.PathInfo;
import org.alfresco.core.model.UserInfo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * Represents a sharedLink entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "sharedlink")
public class SharedLink extends BaseEntity {
    @Id
    private String id;
    private String fileId;
    private String name;
    private String title;
    private String description;
    private Date modifiedAt;
    private UserInfo modifiedByUser;
    private UserInfo sharedByUser;
    private ContentInfo content;
    private List<String> allowableOperations;
    private List<String> allowableOperationsOnTarget;
    private Boolean isFavorite;
    private Object properties;
    private List<String> aspectNames;
    private PathInfo path;
}
