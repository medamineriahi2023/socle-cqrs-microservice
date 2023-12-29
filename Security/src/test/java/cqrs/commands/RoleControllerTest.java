package cqrs.commands;

import com.oga.cqrs.command.commands.role.*;
import com.oga.cqrs.command.domain.RoleAggregate;
import com.oga.cqrs.command.handlers.role.RoleCommandHandlers;
import com.oga.cqrs.command.infrastructure.configuration.*;
import com.oga.cqrs.command.rest.dto.Role;
import com.oga.cqrsref.handlers.EventSourcingHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class RoleControllerTest {

  private Logger logger = LoggerFactory.getLogger(RoleControllerTest.class);

  private RoleCommandHandlers commandHandlers;

  private KeycloakGeneric keycloakGeneric;
  private EventSourcingHandler<RoleAggregate> eventSourcingHandler;
  private KeycloakConfig KeycloakConfig;


  @BeforeEach
  void setUp() {
    KeycloakConfig = new KeycloakConfig("http://localhost:8080/", "master", "admin-cli", "cmRf6eMybVPxXL220tAB8tPZTjhjkDdf", "admin", "admin");

    keycloakGeneric = new KeycloakGeneric();
    eventSourcingHandler = mock(EventSourcingHandler.class);
    commandHandlers = new RoleCommandHandlers(eventSourcingHandler, KeycloakConfig, keycloakGeneric);

  }

  @Test
  void handle_CreateRoleCommand() {

    var id = UUID.randomUUID().toString();
    Role role = Role.builder().name("yasmine").description("rr").build();
    // Arrange
    CreateRoleCommand CreateRoleCommand = new CreateRoleCommand(id, role);

    // Act
    commandHandlers.handle(CreateRoleCommand);

    verify(eventSourcingHandler, times(1)).save(any(RoleAggregate.class));

  }
  @Test
  void handle_UpdateRoleCommand() {

    var id = UUID.randomUUID().toString();
    Role role = Role.builder().id("412692f8-5bfb-4659-8a9a-7a6e44ce48d4").name("yasmine").description("rr").build();
    // Arrange
    UpdateRoleCommand UpdateRoleCommand = new UpdateRoleCommand(id, role);

    // Act
    commandHandlers.handle(UpdateRoleCommand);

    verify(eventSourcingHandler, times(1)).save(any(RoleAggregate.class));

  }

  @Test
  void handle_DeleteRoleCommand() {

    var id = UUID.randomUUID().toString();

    DeleteRoleCommand DeleteRoleCommand = new DeleteRoleCommand(id,"f8f4587e-0971-4630-8765-d2f7acae6743");

    // Act
    commandHandlers.handle(DeleteRoleCommand);

    verify(eventSourcingHandler, times(1)).save(any(RoleAggregate.class));


  }
}
