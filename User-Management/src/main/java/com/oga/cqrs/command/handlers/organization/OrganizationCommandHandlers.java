package com.oga.cqrs.command.handlers.organization;

import com.oga.cqrs.command.commands.organization.CreateOrganizationCommand;
import com.oga.cqrs.command.domain.GroupAggregate;
import com.oga.cqrs.command.domain.OrganizationAggregate;
import com.oga.cqrs.command.infrastructure.configuration.KeycloakConfig;
import com.oga.cqrs.command.infrastructure.configuration.KeycloakGeneric;
import com.oga.cqrsref.handlers.EventSourcingHandler;
import io.phasetwo.client.PhaseTwo;
import io.phasetwo.client.openapi.model.OrganizationRepresentation;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableJpaRepositories("com.oga.cqrs.command.rest.repository")
@ComponentScan("com.oga.cqrsref.handlers")
public class OrganizationCommandHandlers implements OrganizationCommandHandlerInterface {

  private final EventSourcingHandler<GroupAggregate> eventSourcingHandler;
  RealmResource realmResource;

  private final KeycloakGeneric keycloakGeneric;
  private final KeycloakConfig keycloakConfig;

  @Autowired
  public OrganizationCommandHandlers(EventSourcingHandler<GroupAggregate> eventSourcingHandler, KeycloakConfig keycloakConfig, KeycloakGeneric keycloakGeneric) {
    this.eventSourcingHandler = eventSourcingHandler;
    this.keycloakGeneric=keycloakGeneric;
    this.keycloakConfig= keycloakConfig;
    this.realmResource = keycloakConfig.getInstance().realm(keycloakConfig.getRealm());

  }
  @Override
  public void handle(CreateOrganizationCommand createOrganizationCommand) {
    PhaseTwo phaseTwo = new PhaseTwo(keycloakConfig.getInstance(), keycloakConfig.getServerURL());
    OrganizationRepresentation org = new OrganizationRepresentation().name(createOrganizationCommand.getOrganization().getName()).
            displayName(createOrganizationCommand.getOrganization().getDisplayName()).
            realm(keycloakConfig.getRealm());
    System.out.println(realmResource.groups().groups().get(0).getName());
    String orgId = phaseTwo.organizations(keycloakConfig.getRealm()).create(org);
    System.out.println(orgId);
    OrganizationAggregate organizationAggregate = new OrganizationAggregate(createOrganizationCommand);
    eventSourcingHandler.save(organizationAggregate);

  }
}
