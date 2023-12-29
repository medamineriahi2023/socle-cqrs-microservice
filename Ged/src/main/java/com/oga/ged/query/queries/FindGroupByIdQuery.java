package com.oga.ged.query.queries;

import com.oga.cqrsref.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * query class for finding a group by ID.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindGroupByIdQuery extends BaseQuery {
    private String groupId;
}
