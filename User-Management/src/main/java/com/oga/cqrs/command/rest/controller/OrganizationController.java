package com.oga.cqrs.command.rest.controller;

import com.oga.cqrs.command.commands.group.CreateGroupCommand;
import com.oga.cqrs.command.commands.organization.CreateOrganizationCommand;
import com.oga.cqrs.command.rest.dto.Group;
import com.oga.cqrs.command.rest.dto.Organization;
import com.oga.cqrs.command.rest.response.BasicResponse;
import com.oga.cqrsref.infrastructure.CommandDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static com.oga.cqrs.command.rest.constant.Constant.SUCCESS_MESSAGE_CREATION_ORGANIZATION;

@ComponentScan(basePackages="com.oga.cqrsref.controller")
@RestController
@RequestMapping(path = "/security/v1")
@Slf4j
public class OrganizationController {

    private final CommandDispatcher commandDispatcher;
    @Autowired
    public OrganizationController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }



    @PostMapping("org")
    public ResponseEntity<BasicResponse> createOrganization(@Valid @RequestBody Organization organization) {
        CreateOrganizationCommand command=new CreateOrganizationCommand();
        var id = UUID.randomUUID().toString();
        command.setId(id);
        command.setOrganization(organization);
        commandDispatcher.send(command);

        return new ResponseEntity<>(new BasicResponse(SUCCESS_MESSAGE_CREATION_ORGANIZATION, command.getId()), HttpStatus.CREATED);


    }
}
