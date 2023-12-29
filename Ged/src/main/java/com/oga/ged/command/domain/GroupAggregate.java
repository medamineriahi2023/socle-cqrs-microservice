package com.oga.ged.command.domain;

import com.oga.cqrsref.domain.AggregateRoot;
import com.oga.ged.command.commands.AssignPersonToGroupCommand;
import com.oga.ged.command.commands.CreateGroupCommand;
import com.oga.ged.command.commands.DeleteGroupCommand;
import com.oga.ged.command.commands.RemovePersonFromGroupCommand;
import com.oga.ged.command.events.GroupCreatedEvent;
import com.oga.ged.command.events.GroupDeletedEvent;
import com.oga.ged.command.events.PersonAssignedToGroupEvent;
import com.oga.ged.command.events.PersonRemovedFromGroupEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an aggregate in the domain model for managing groups.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupAggregate extends AggregateRoot {
    private String  id;

    public GroupAggregate(CreateGroupCommand command) {
        raiseEvent(GroupCreatedEvent.builder()
                .groupId(command.getGroupId())
                .identifier(command.getId())
                .displayName(command.getDisplayName())
                .isRoot(command.getIsRoot())
                .parentIds(command.getParentIds())
                .zones(command.getZones())
                .build());
    }

    public void apply (GroupCreatedEvent event)
    {
        this.id=event.getIdentifier();

    }

    public GroupAggregate(DeleteGroupCommand command) {
        raiseEvent(GroupDeletedEvent.builder()
                .groupId(command.getGroupId())
                .identifier(command.getId())
                .build());
    }

    public void apply(GroupDeletedEvent event)
    {
        this.id=event.getIdentifier();
    }

    public GroupAggregate(AssignPersonToGroupCommand command) {
        raiseEvent(PersonAssignedToGroupEvent.builder()
                .groupMembershipId(command.getGroupMembershipId())
                .groupId(command.getGroupId())
                .personId(command.getPersonId())
                .identifier(command.getId())
                .build());
    }

    public void apply(PersonAssignedToGroupEvent event)
    {
        this.id=event.getIdentifier();
    }

    public GroupAggregate(RemovePersonFromGroupCommand command) {
        raiseEvent(PersonRemovedFromGroupEvent.builder()
                .groupId(command.getGroupId())
                .personId(command.getPersonId())
                .identifier(command.getId())
                .build());
    }

    public void apply(PersonRemovedFromGroupEvent event)
    {
        this.id=event.getIdentifier();
    }

}
