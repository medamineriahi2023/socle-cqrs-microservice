package com.oga.cqrs.query.rest.controller;

import com.oga.cqrs.command.rest.constant.Constant;
import com.oga.cqrs.query.queries.user.FindAllUsers;
import com.oga.cqrs.query.queries.user.FindUserById;
import com.oga.cqrs.query.rest.dto.UsersDetails;
import com.oga.cqrs.query.rest.response.QueryUserResponse;
import com.oga.cqrsref.infrastructure.IQueryDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
/**
 * Controller for handling HTTP requests related to queries.
 */
@ComponentScan(basePackages="com.oga.cqrsref.controller")
@RestController
@RequestMapping(path = "/security/v1")
public class UserQueryController {

    private final Logger logger = Logger.getLogger(UserQueryController.class.getName());

    @Autowired
    private IQueryDispatcher queryDispatcher;

    /**
     * @return ResponseEntity containing a response object of type QueryUserResponse
     * queryDispatcher.send(new FindAllUsers()) uses the queryDispatcher to send a query of type FindAllUsers
     * (users == null ) this condition checks if the users list is either null or empty
     * if users list is null , a status of {@link HttpStatus#NO_CONTENT} is returned
     * if users is not null , a status of {@link HttpStatus#OK} is returned
     * QueryUserResponse.builder()  starts building a response object using the QueryUserResponse builder
     * users(users): This sets the users property of the response object with the  list
     */
    @GetMapping("users")
    public ResponseEntity<QueryUserResponse> getAllUsers() {
        List<UsersDetails> users = queryDispatcher.send(new FindAllUsers());
        if (users == null ) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        var response = QueryUserResponse.builder()
            .users(users)

            .message(MessageFormat.format(Constant.GET_ALL_USERS_SUCCESS, users.size()))
            .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @return ResponseEntity containing a response object of type QueryUserResponse
     * @param id
     * (User) queryDispatcher.sendObject(new FindUserById(id))  uses the queryDispatcher to send a query of type FindUserById with the provided id to retrieve a single user
     * if user  is null , a status of {@link HttpStatus#NO_CONTENT} is returned
     * if user is not null , a status of {@link HttpStatus#OK} is returned
     * QueryUserResponse.builder()  starts building a response object using the QueryUserResponse builder
     * users(users): This sets the users property of the response object with the  list
     */
    @GetMapping(path = "users/{id}")
    public ResponseEntity<QueryUserResponse> getUserById(@PathVariable String id) {

        UsersDetails user = (UsersDetails) queryDispatcher.sendObject(new FindUserById(id));
        if (user == null ) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        List<UsersDetails> users =new ArrayList<>() {
        };
        users.add(user);
        var response = QueryUserResponse.builder()
            .users(users)
            .message(MessageFormat.format(Constant.GET_USER_BY_ID_SUCCESS, user.getUser()))
            .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
