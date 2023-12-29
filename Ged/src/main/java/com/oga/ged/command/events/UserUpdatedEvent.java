package com.oga.ged.command.events;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.alfresco.core.model.Company;

import java.util.List;

/**
 * Represents a user updated event.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class UserUpdatedEvent extends BaseEvent {
    private String userId;
    private String identifier;
    private String firstName;
    private String lastName;
    private String description;
    private String email;
    private String skypeId;
    private String googleId;
    private String instantMessageId;
    private String jobTitle;
    private String location;
    private Company company;
    private String mobile;
    private String telephone;
    private String userStatus;
    private Boolean enabled;
    private Boolean emailNotificationsEnabled;
    private String password;
    private String oldPassword;
    private List<String> aspectNames;
    private Object properties;
}
