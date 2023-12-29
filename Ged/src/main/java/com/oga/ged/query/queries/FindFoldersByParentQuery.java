package com.oga.ged.query.queries;

import com.oga.cqrsref.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * query class for finding folders by parent ID.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindFoldersByParentQuery extends BaseQuery {
    private String parentId;

}
