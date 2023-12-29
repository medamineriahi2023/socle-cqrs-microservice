package com.oga.ged.query.rest.dto;

import com.oga.cqrsref.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alfresco.core.model.Capabilities;
import org.alfresco.core.model.Company;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Represents a user entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "user")
public class User extends BaseEntity {
    @Id
    private String id;
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
