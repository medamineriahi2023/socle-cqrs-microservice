package com.oga.ged.query.queries;

import com.oga.cqrsref.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * query class for listing groups associated with a person.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListGroupsForPersonQuery extends BaseQuery {
    private String personId;
}