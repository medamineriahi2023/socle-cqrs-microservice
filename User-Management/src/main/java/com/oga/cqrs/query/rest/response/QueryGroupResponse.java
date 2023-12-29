package com.oga.cqrs.query.rest.response;

import com.oga.cqrs.query.rest.dto.Group;
import com.oga.cqrsref.Response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;


/**
 * A response object for queries that return a list of employees.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class QueryGroupResponse extends BaseResponse {

  /**
   * The list of group returned by the query.
   */
  private List<Group> groups;



}
