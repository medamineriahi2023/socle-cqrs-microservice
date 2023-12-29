package com.oga.ged.query.rest.dto;

import com.oga.cqrsref.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Represents a group entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "group")
public class Group extends BaseEntity {
    @Id
    private String id;
    private String displayName;
    private Boolean isRoot;
    private List<String> parentIds;
    private List<String> zones;
}
