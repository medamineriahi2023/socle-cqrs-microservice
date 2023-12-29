package com.oga.ged.query.rest.response;

import com.oga.ged.query.rest.dto.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FindGroupMembersResponse extends BaseResponse{
    private List<User> groupMembers;
}
