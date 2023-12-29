package com.oga.ged.query.rest.dto;

import com.oga.cqrsref.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a PersonGroupAssignment entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "persongroupassignment")
public class PersonGroupAssignment extends BaseEntity {
    @Id
    private String id;
    private String groupId;
    private String personId;
}
