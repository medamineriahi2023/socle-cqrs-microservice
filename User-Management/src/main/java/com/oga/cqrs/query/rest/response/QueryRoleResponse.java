package com.oga.cqrs.query.rest.response;

import com.oga.cqrs.query.rest.dto.Role;
import com.oga.cqrsref.Response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class QueryRoleResponse  extends BaseResponse {

  /**
   * The list of role returned by the query.
   */
  private List<Role> roles;



}
