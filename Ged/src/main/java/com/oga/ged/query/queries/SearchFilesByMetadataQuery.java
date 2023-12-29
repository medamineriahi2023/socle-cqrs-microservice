package com.oga.ged.query.queries;

import com.oga.cqrsref.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * query class for searching files by metadata.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchFilesByMetadataQuery extends BaseQuery {
    private String name;
    private String title;
    private String description;
    private String created;
    private String creator;
    private String modified;
    private String modifier;
    private String mimeType;
    private String mimeTypeName;
    private Long sizeInBytes;
}
