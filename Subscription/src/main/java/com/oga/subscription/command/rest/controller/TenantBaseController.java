package com.oga.subscription.command.rest.controller;


import com.oga.cqrsref.infrastructure.CommandDispatcher;
import com.oga.subscription.command.commands.tenant.CreateTenantCommand;
import com.oga.subscription.command.rest.constant.Constant;
import com.oga.subscription.command.rest.response.BaseResponse;
import com.oga.subscription.command.rest.response.CreateTenantResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping(path = "/api/v1/catalog")
@Slf4j
public class TenantBaseController {

    private final CommandDispatcher commandDispatcher;
    /**
     * Constructs a TenantBaseController instance.
     *
     * @param commandDispatcher The command dispatcher for sending commands.
     */
    @Autowired
    public TenantBaseController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    /**
     * Handles a request to create a new product.
     *
     * @param command The CreateTenantCommand object representing the product to create.
     * @return A ResponseEntity containing a BaseResponse object with a message and the ID of the new product.
     *
     * If the request is successful, a status of {@link HttpStatus#CREATED} is returned.
     * If the request is unsuccessful due to a bad request, a status of {@link HttpStatus#BAD_REQUEST} is returned.
     * If an error occurs during processing, a status of {@link HttpStatus#INTERNAL_SERVER_ERROR} is returned.
     */

    @PostMapping(path = "/tenant")
    public ResponseEntity<BaseResponse> createTenant(@RequestBody CreateTenantCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        commandDispatcher.send(command);
        return new ResponseEntity<>(new CreateTenantResponse(Constant.SUCCESS_MESSAGE, command.getId()), HttpStatus.CREATED);
    }
}
