package com.oga.ged.command.events;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.alfresco.core.model.Capabilities;
import org.alfresco.core.model.Company;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Represents a user created event.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class UserCreatedEvent extends BaseEvent {
    private String userId;
    private String identifier;
    private String password;
    private String firstName;
    private String lastName;
    private String displayName;
    private String description;
    private String avatarId;
    private String email;
    private String skypeId;
    private String googleId;
    private String instantMessageId;
    private String jobTitle;
    private String location;
    private Company company;
    private String mobile;
    private String telephone;
    private OffsetDateTime statusUpdatedAt;
    private String userStatus;
    private Boolean enabled;
    private Boolean emailNotificationsEnabled;
    private List<String> aspectNames;
    private Object properties;
    private Capabilities capabilities;
}
