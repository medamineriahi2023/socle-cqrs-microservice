package com.oga.ged.query.queries;

import com.oga.cqrsref.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * query class for finding users by ID.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindUserByIdQuery extends BaseQuery {
    private String userId;
}
