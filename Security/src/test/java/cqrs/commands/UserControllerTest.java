package cqrs.commands;


import com.oga.cqrs.command.commands.user.*;
import com.oga.cqrs.command.domain.UserAggregate;
import com.oga.cqrs.command.handlers.user.UserCommandHandlers;
import com.oga.cqrs.command.infrastructure.configuration.KeycloakConfig;
import com.oga.cqrs.command.infrastructure.configuration.KeycloakGeneric;
import com.oga.cqrs.command.rest.dto.Login;
import com.oga.cqrs.command.rest.dto.TokenRequestLogin;
import com.oga.cqrs.command.rest.dto.User;
import com.oga.cqrsref.handlers.EventSourcingHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;




public class UserControllerTest {
  private Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

  private UserCommandHandlers commandHandlers;

  private KeycloakGeneric keycloakGeneric;
  private EventSourcingHandler<UserAggregate> eventSourcingHandler;
  private KeycloakConfig KeycloakConfig;

  @BeforeEach
  void setUp() {

    KeycloakConfig = new KeycloakConfig("http://localhost:8080/", "master", "admin-cli", "cmRf6eMybVPxXL220tAB8tPZTjhjkDdf", "admin", "admin");



    keycloakGeneric=new KeycloakGeneric();
    eventSourcingHandler = mock(EventSourcingHandler.class);
    commandHandlers = new UserCommandHandlers(eventSourcingHandler, KeycloakConfig,keycloakGeneric);

  }

  @Test
  void handle_CreateUserCommand() {

    var id = UUID.randomUUID().toString();

    User user= User.builder().userName("SSrfS").firstName("yasmina").lastName("mestiri").email("SSrfS@gmail.com").password("1234").phone("47569213").build();

    // Arrange
    CreateUserCommand CreateUserCommand = new CreateUserCommand(id,user);

    // Act
    commandHandlers.handle(CreateUserCommand);

    verify(eventSourcingHandler, times(1)).save(any(UserAggregate.class));


  }



  @Test
  void handle_ModifyUserCommand() {
    // Arrange
    var id = UUID.randomUUID().toString();
    User user= User.builder().id("3a28be20-b9d8-43a2-ae8c-f45377d6c604").userName("aaaa").firstName("yasmina").lastName("mestiri").email("aaaa@gmail.com").password("1234").phone("47569213").build();
    // Arrange
    UpdateUserCommand UpdateUserCommand = new UpdateUserCommand(id,user);

    // Act
    commandHandlers.handle(UpdateUserCommand);

    // Assert

    verify(eventSourcingHandler, times(1)).save(any(UserAggregate.class));



  }
  @Test
  void handle_DeleteUserCommand() {
    // Arrange
    var id = UUID.randomUUID().toString();

    // Arrange
    DeleteUserCommand DeleteUserCommand = new DeleteUserCommand(id,"5a44843d-c378-4f48-84d5-eb50c8f307ea");

    // Act
    commandHandlers.handle(DeleteUserCommand);
    // Assert

    verify(eventSourcingHandler, times(1)).save(any(UserAggregate.class));

    // Indicate test success
  }

  @Test
  void handle_UpdateUserPasswordCommand() {
    // Arrange
    var id = UUID.randomUUID().toString();
    Login login= Login.builder().iduser("3a28be20-b9d8-43a2-ae8c-f45377d6c604").password("aaaa").build();
    // Arrange
    // Arrange
    UpdatePasswordCommand UpdatePasswordCommand = new UpdatePasswordCommand(id,login);

    // Act
    commandHandlers.handle(UpdatePasswordCommand);
    // Assert

    verify(eventSourcingHandler, times(1)).save(any(UserAggregate.class));

    // Indicate test success
  }


  @Test
  void handle_CreateUserByAdminCommand() {

    var id = UUID.randomUUID().toString();
    User user= User.builder().userName("ggggggg").firstName("ttt").lastName("mestiri").email("yasmine.mestiri@esprit.tn").password("").phone("47569213").build();
    // Arrange
    CreateUserByAdmin CreateUserByAdmin = new CreateUserByAdmin(id,user);

    // Act
    commandHandlers.handle(CreateUserByAdmin);

    verify(eventSourcingHandler, times(1)).save(any(UserAggregate.class));


  }
  @Test
  void handle_GenerateTokenCommand() {

    var id = UUID.randomUUID().toString();
    TokenRequestLogin user= TokenRequestLogin.builder().username("yasmine").password("test").build();
    // Arrange
    CreateTokenRequestCommand CreateTokenRequestCommand = new CreateTokenRequestCommand(id,user);

    // Act
    commandHandlers.handle(CreateTokenRequestCommand);

    verify(eventSourcingHandler, times(1)).save(any(UserAggregate.class));


  }

  @Test
  void handle_logout() {

    var id = UUID.randomUUID().toString();
    CreateLogoutRequestCommand command=new CreateLogoutRequestCommand(id,"cb84c534-7472-4da2-8568-df0262ad56c7");
        // Arrange

    // Act
    commandHandlers.handle(command);

    verify(eventSourcingHandler, times(1)).save(any(UserAggregate.class));


  }

  @Test
  void handle_ForgetPassword() {

    var id = UUID.randomUUID().toString();
    ForgetPasswordCommand command=new ForgetPasswordCommand(id,"yasmine");

    // Act
    commandHandlers.handle(command);

    verify(eventSourcingHandler, times(1)).save(any(UserAggregate.class));


  }

  @Test
  void handle_Affectroles() {
   List<String> ids=new ArrayList<>();
   ids.add("9cb52268-9f5c-45e2-aab5-ba77a3ecaf85");


    var id = UUID.randomUUID().toString();
    AffectRolesCommand command=new AffectRolesCommand(id,"1ffcf3b0-30c3-4723-baad-24cef746e187",ids);

    // Act
    commandHandlers.handle(command);

    verify(eventSourcingHandler, times(1)).save(any(UserAggregate.class));


  }
}
