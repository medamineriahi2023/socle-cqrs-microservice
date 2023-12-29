package com.oga.ged.command.domain;

import com.oga.cqrsref.domain.AggregateRoot;
import com.oga.ged.command.commands.CreateUserCommand;
import com.oga.ged.command.commands.UpdateUserCommand;
import com.oga.ged.command.events.UserCreatedEvent;
import com.oga.ged.command.events.UserUpdatedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an aggregate in the domain model for managing users.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAggregate extends AggregateRoot {
    private String  id;
    public UserAggregate(CreateUserCommand command) {
        raiseEvent(UserCreatedEvent.builder()
                .userId(command.getUserId())
                .identifier(command.getId())
                .password(command.getPassword())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .displayName(command.getDisplayName())
                .description(command.getDescription())
                .avatarId(command.getAvatarId())
                .email(command.getEmail())
                .skypeId(command.getSkypeId())
                .googleId(command.getGoogleId())
                .instantMessageId(command.getInstantMessageId())
                .jobTitle(command.getJobTitle())
                .location(command.getLocation())
                .company(command.getCompany())
                .mobile(command.getMobile())
                .telephone(command.getTelephone())
                .statusUpdatedAt(command.getStatusUpdatedAt())
                .userStatus(command.getUserStatus())
                .enabled(command.getEnabled())
                .emailNotificationsEnabled(command.getEmailNotificationsEnabled())
                .aspectNames(command.getAspectNames())
                .properties(command.getProperties())
                .capabilities(command.getCapabilities())
                .build());
    }

    public void apply (UserCreatedEvent event)
    {
        this.id=event.getIdentifier();

    }

    public UserAggregate(UpdateUserCommand command) {
        raiseEvent(UserUpdatedEvent.builder()
                .identifier(command.getId())
                .userId(command.getUserId())
                .password(command.getPassword())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .description(command.getDescription())
                .email(command.getEmail())
                .skypeId(command.getSkypeId())
                .googleId(command.getGoogleId())
                .instantMessageId(command.getInstantMessageId())
                .jobTitle(command.getJobTitle())
                .location(command.getLocation())
                .company(command.getCompany())
                .mobile(command.getMobile())
                .telephone(command.getTelephone())
                .userStatus(command.getUserStatus())
                .enabled(command.getEnabled())
                .emailNotificationsEnabled(command.getEmailNotificationsEnabled())
                .aspectNames(command.getAspectNames())
                .properties(command.getProperties())
                .build());
    }

    public void apply(UserUpdatedEvent event)
    {
        this.id=event.getIdentifier();
    }
}
