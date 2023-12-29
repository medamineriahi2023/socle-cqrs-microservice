package com.oga.subscription.query.handlers;

import com.oga.cqrsref.domain.BaseEntity;
import com.oga.cqrsref.queries.BaseQuery;

import java.util.List;

/**
 * Interface for handling queries .
 */
public interface ProductQueryHandler {

    /**
     * Handles a query.
     *
     * @param baseQuery the query to handle
     * @return a list of entities that match the query criteria
     */
    List<BaseEntity> handle(BaseQuery baseQuery);
}
