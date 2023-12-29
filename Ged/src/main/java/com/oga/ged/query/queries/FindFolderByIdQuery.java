package com.oga.ged.query.queries;

import com.oga.cqrsref.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * query class for finding folders by ID.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindFolderByIdQuery extends BaseQuery {
    private String folderId;
}
