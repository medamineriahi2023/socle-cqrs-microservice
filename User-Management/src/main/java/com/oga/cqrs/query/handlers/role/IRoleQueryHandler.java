package com.oga.cqrs.query.handlers.role;

import com.oga.cqrs.query.queries.role.FindRoleById;
import com.oga.cqrs.query.rest.dto.Role;
import com.oga.cqrsref.domain.BaseEntity;
import com.oga.cqrsref.queries.BaseQuery;

import java.util.List;

public interface IRoleQueryHandler {
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
   * @param FindRoleById the query to handle
   * @return en entity that match the query criteria
   */
  Role handle(FindRoleById FindRoleById);
}
