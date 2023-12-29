package com.oga.ged.command.commands;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alfresco.core.model.Capabilities;
import org.alfresco.core.model.Company;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Represents a command for creating a user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserCommand extends BaseCommand {
    private String userId;
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
