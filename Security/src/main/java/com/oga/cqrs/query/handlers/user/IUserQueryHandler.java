package com.oga.cqrs.query.handlers.user;

import com.oga.cqrs.query.queries.user.FindUserById;
import com.oga.cqrs.query.rest.dto.UsersDetails;
import com.oga.cqrsref.domain.BaseEntity;
import com.oga.cqrsref.queries.BaseQuery;

import java.util.List;

/**
 * Interface for handling queries .
 */
public interface IUserQueryHandler {


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
     * @param FindUserById the query to handle
     * @return en entity that match the query criteria
     */
    UsersDetails handle(FindUserById FindUserById);


}
