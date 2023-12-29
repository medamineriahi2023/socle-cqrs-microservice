package com.oga.ged.query.rest.response;

import com.oga.ged.query.rest.dto.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FindGroupsForPersonResponse extends BaseResponse{
    private List<Group> groups;
}