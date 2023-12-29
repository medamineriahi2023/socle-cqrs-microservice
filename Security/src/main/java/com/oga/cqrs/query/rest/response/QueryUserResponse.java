package com.oga.cqrs.query.rest.response;

import com.oga.cqrs.query.rest.dto.UsersDetails;
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
public class QueryUserResponse extends BaseResponse {

    /**
     * The list of user returned by the query.
     */
    private List<UsersDetails> users;

}
