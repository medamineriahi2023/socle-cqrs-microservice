package com.oga.ged;

import com.oga.cqrsref.infrastructure.CommandDispatcher;
import com.oga.cqrsref.infrastructure.QueryDispatcher;
import com.oga.ged.command.commands.*;
import com.oga.ged.command.handlers.ICommandHandler;
import com.oga.ged.query.handlers.IQueryHandler;
import com.oga.ged.query.queries.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

import javax.annotation.PostConstruct;

@EnableEurekaClient
@SpringBootApplication
@EnableKafka
@ComponentScan("com.oga.cqrsref.infrastructure")
@ComponentScan("com.oga.ged.command.handlers")
@ComponentScan("com.oga.ged.query.handlers")
public class GedApplication {

    private final ICommandHandler commandHandler;
    private final IQueryHandler queryHandler;
    @Autowired
    private CommandDispatcher commandDispatcher;

    @Autowired
    private  QueryDispatcher queryDispatcher;

    public GedApplication(ICommandHandler commandHandler, IQueryHandler queryHandler) {
        this.commandHandler = commandHandler;
        this.queryHandler = queryHandler;
    }


    @PostConstruct
    public void registerHandlers() {
        commandDispatcher.registerHandler(CreateFolderCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(UpdateFolderCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(DeleteFolderCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(MoveFolderCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(CreateFileCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(UpdateFileCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(DeleteFileCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(MoveFileCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(CreateUserCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(UpdateUserCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(CreateGroupCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(DeleteGroupCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(AssignPersonToGroupCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(RemovePersonFromGroupCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(CreateSharedLinkCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(DeleteSharedLinkCommand.class, commandHandler::handle);
        queryDispatcher.registerHandler(FindFoldersByParentQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindFolderByIdQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindFileByIdQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(SearchFoldersByMetadataQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(SearchFilesByMetadataQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindUserByIdQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindAllUsersQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindFilesByFolderQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindAllFoldersQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(CountFilesByFolderQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindGroupByIdQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(ListGroupMembersQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(ListGroupsForPersonQuery.class, queryHandler::handle);





    }
    
    public static void main(String[] args) {
        SpringApplication.run(GedApplication.class, args);
    }
}