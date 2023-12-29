package com.oga.cqrs.query.rest.dto;

import com.oga.cqrsref.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "organization")
public class Organization extends BaseEntity {
    @Id
    private String id;
    private String name;
    private String displayName;
    private String url;
    private String realm;
}
