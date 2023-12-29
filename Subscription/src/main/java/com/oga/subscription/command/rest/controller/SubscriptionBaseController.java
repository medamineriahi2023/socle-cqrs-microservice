package com.oga.subscription.command.rest.controller;


import com.oga.cqrsref.infrastructure.CommandDispatcher;
import com.oga.subscription.command.commands.subscription.CreateSubscriptionCommand;
import com.oga.subscription.command.rest.constant.Constant;
import com.oga.subscription.command.rest.response.BaseResponse;
import com.oga.subscription.command.rest.response.CreateSubscriptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping(path = "/api/v1/subscription")
@Slf4j
public class SubscriptionBaseController {

    private final CommandDispatcher commandDispatcher;
    /**
     * Constructs a SubscriptionBaseController instance.
     *
     * @param commandDispatcher The command dispatcher for sending commands.
     */
    @Autowired
    public SubscriptionBaseController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    /**
     * Handles a request to create a new product.
     *
     * @param command The CreateSubscriptionCommand object representing the product to create.
     * @return A ResponseEntity containing a BaseResponse object with a message and the ID of the new product.
     *
     * If the request is successful, a status of {@link HttpStatus#CREATED} is returned.
     * If the request is unsuccessful due to a bad request, a status of {@link HttpStatus#BAD_REQUEST} is returned.
     * If an error occurs during processing, a status of {@link HttpStatus#INTERNAL_SERVER_ERROR} is returned.
     */

    @PostMapping(path = "")
    public ResponseEntity<BaseResponse> createSubscription(@RequestBody CreateSubscriptionCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        commandDispatcher.send(command);
        return new ResponseEntity<>(new CreateSubscriptionResponse(Constant.SUCCESS_MESSAGE, command.getId()), HttpStatus.CREATED);
    }

}
