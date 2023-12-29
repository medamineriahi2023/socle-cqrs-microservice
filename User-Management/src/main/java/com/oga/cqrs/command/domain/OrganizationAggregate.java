package com.oga.cqrs.command.domain;

import com.oga.cqrs.command.commands.organization.CreateOrganizationCommand;
import com.oga.cqrs.command.events.organizations.OrganizationCreated;
import com.oga.cqrs.command.rest.dto.Organization;
import com.oga.cqrsref.domain.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationAggregate extends AggregateRoot {
    private String  id;
    private Organization organization;


    public OrganizationAggregate(CreateOrganizationCommand command) {
        raiseEvent(OrganizationCreated.builder()
                .identifier(command.getId())
                .organization(command.getOrganization())
                .build());
    }

    public void apply (OrganizationCreated event)
    {
        this.id=event.getIdentifier();

    }

}
