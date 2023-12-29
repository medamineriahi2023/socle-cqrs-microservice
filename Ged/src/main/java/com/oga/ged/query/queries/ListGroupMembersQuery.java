package com.oga.ged.query.queries;

import com.oga.cqrsref.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * query class for listing group members.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListGroupMembersQuery extends BaseQuery {
    private String groupId;
}
