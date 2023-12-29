package com.oga.cqrs.query.handlers.group;


import com.oga.cqrs.query.queries.group.FindGroupById;
import com.oga.cqrs.query.rest.dto.Group;
import com.oga.cqrsref.domain.BaseEntity;
import com.oga.cqrsref.queries.BaseQuery;

import java.util.List;


/**
 * Interface for handling queries .
 */
public interface IGroupQueryHandler {

    /**
     * Handles a query.
     *
     * @param baseQuery the query to handle
     * @return a list of entities that match the query criteria
     */
    List<BaseEntity> handle(BaseQuery baseQuery);

    /**
     * Handles a query.
     *
     * @param FindGroupById the query to handle
     * @return en entity that match the query criteria
     */
    Group handle(FindGroupById FindGroupById);
}
