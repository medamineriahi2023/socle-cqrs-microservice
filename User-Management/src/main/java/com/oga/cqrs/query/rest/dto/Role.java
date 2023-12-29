package com.oga.cqrs.query.rest.dto;

import com.oga.cqrsref.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "role")
public class Role extends BaseEntity {

  @Id
  private String	      id;

  @NotBlank(message = "name is required")
  private String	      name;

  private String	      description;

  private List<Role> permissions = new ArrayList<>();
}
