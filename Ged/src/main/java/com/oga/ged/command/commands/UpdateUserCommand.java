package com.oga.ged.command.commands;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alfresco.core.model.Company;

import java.util.List;

/**
 * Represents a command for updating a user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserCommand extends BaseCommand {
    private String userId;
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
