package com.oga.cqrs.query.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oga.cqrsref.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "user")
public class User  extends BaseEntity {
  @Id
  private String	      id;
  private String	      firstName;

  private String	      lastName;

  private String	      email;
  private String	      userName;



  private String	      password;


  private String	      phone;

  @JsonIgnore
  private List<String> rolesids;

  /**
   * Constructor for creating an instance of the user with a name and last name.
   * @param firstName The firstName of the user.
   * @param lastName The last name of the user.
   * @param email The email of the user
   * @param userName The userName of the user.
   * @param password The password of the user.
   * @param phone The phone of the user
   */
  public User(String firstName, String lastName, String email, String userName, String password, String phone) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.userName = userName;
    this.password = password;
    this.phone = phone;
  }
}
