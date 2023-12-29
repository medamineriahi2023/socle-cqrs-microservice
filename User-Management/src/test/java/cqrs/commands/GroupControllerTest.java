package cqrs.commands;

import com.oga.cqrs.command.commands.group.*;
import com.oga.cqrs.command.domain.GroupAggregate;
import com.oga.cqrs.command.handlers.group.GroupCommandHandlers;
import com.oga.cqrs.command.infrastructure.configuration.*;
import com.oga.cqrs.command.rest.dto.Group;
import com.oga.cqrsref.handlers.EventSourcingHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GroupControllerTest {
  private Logger logger = LoggerFactory.getLogger(GroupControllerTest.class);

  private GroupCommandHandlers commandHandlers;

  private KeycloakGeneric keycloakGeneric;
  private EventSourcingHandler<GroupAggregate> eventSourcingHandler;
  private KeycloakConfig KeycloakConfig;

  @BeforeEach
  void setUp() {
    KeycloakConfig = new KeycloakConfig("http://localhost:8080/", "master", "admin-cli", "PXfFn2lclqCV6Eg5d43C3VcDvfvT76GX", "admin", "admin");

    keycloakGeneric=new KeycloakGeneric();
    eventSourcingHandler = mock(EventSourcingHandler.class);
    commandHandlers = new GroupCommandHandlers(eventSourcingHandler, KeycloakConfig,keycloakGeneric);

  }
  @Test
  void handle_CreateGroupCommand() {

    var id = UUID.randomUUID().toString();
    Group group= Group.builder().name("SSrfS").build();
    // Arrange
    CreateGroupCommand CreateGroupCommand = new CreateGroupCommand(id,group);

    // Act
    commandHandlers.handle(CreateGroupCommand);

    verify(eventSourcingHandler, times(1)).save(any(GroupAggregate.class));


  }

  @Test
  void handle_UpdateGroupCommand() {

    var id = UUID.randomUUID().toString();
    Group group= Group.builder().id("af7766f8-d709-48cf-8dab-e26fbd34fb6c").name("SSrfS").build();
    // Arrange
    UpdateGroupCommand UpdateGroupCommand = new UpdateGroupCommand(id,group);

    // Act
    commandHandlers.handle(UpdateGroupCommand);

    verify(eventSourcingHandler, times(1)).save(any(GroupAggregate.class));


  }

  @Test
  void handle_DeleteGroupCommand() {

    var id = UUID.randomUUID().toString();

    DeleteGroupCommand DeleteGroupCommand = new DeleteGroupCommand(id,"03473527-822f-4adf-9f82-61b8a571cd7c");

    // Act
    commandHandlers.handle(DeleteGroupCommand);

    verify(eventSourcingHandler, times(1)).save(any(GroupAggregate.class));


  }
}
