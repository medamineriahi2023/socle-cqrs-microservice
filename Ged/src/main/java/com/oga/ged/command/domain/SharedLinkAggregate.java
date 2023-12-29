package com.oga.ged.command.domain;

import com.oga.cqrsref.domain.AggregateRoot;
import com.oga.ged.command.commands.CreateSharedLinkCommand;
import com.oga.ged.command.commands.DeleteSharedLinkCommand;
import com.oga.ged.command.events.SharedLinkCreatedEvent;
import com.oga.ged.command.events.SharedLinkDeletedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an aggregate in the domain model for managing sharedLinks.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharedLinkAggregate extends AggregateRoot {
    private String  id;
    public SharedLinkAggregate(CreateSharedLinkCommand command) {
        raiseEvent(SharedLinkCreatedEvent.builder()
                .sharedLinkId(command.getSharedLinkId())
                .identifier(command.getId())
                .name(command.getName())
                .title(command.getTitle())
                .description(command.getDescription())
                .modifiedAt(command.getModifiedAt())
                .modifiedByUser(command.getModifiedByUser())
                .sharedByUser(command.getSharedByUser())
                .content(command.getContent())
                .allowableOperations(command.getAllowableOperations())
                .allowableOperationsOnTarget(command.getAllowableOperationsOnTarget())
                .isFavorite(command.getIsFavorite())
                .properties(command.getProperties())
                .aspectNames(command.getAspectNames())
                .path(command.getPath())
                .build());
    }

    public void apply (SharedLinkCreatedEvent event)
    {
        this.id=event.getIdentifier();

    }

    public SharedLinkAggregate(DeleteSharedLinkCommand command) {
        raiseEvent(SharedLinkDeletedEvent.builder()
                .sharedLinkId(command.getSharedLinkId())
                .identifier(command.getId())
                .build());
    }

    public void apply(SharedLinkDeletedEvent event)
    {
        this.id=event.getIdentifier();
    }
}
