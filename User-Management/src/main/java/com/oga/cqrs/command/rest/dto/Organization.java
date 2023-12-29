package com.oga.cqrs.command.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Organization {
    private String id;
    private String name;
    private String displayName;
    private String url;
    private String realm;
}
