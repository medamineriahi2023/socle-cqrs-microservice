package com.oga.ged.query.queries;

import com.oga.cqrsref.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * query class for searching folders by metadata.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchFoldersByMetadataQuery extends BaseQuery {
    private String name;
    private String title;
    private String description;
    private String created;
    private String creator;
    private String modified;
    private String modifier;
}
