package com.oga.cqrs.query.rest.dto;

import com.oga.cqrsref.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersDetails extends BaseEntity {
private User user;
private List<Role> roles;

  @Override
  public String toString() {
    return "UserRoles{" +
        "user=" + user +
        ", roles=" + roles +
        '}';
  }
}
