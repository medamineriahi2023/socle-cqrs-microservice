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
public class FindAllUsersResponse extends BaseResponse{
    private List<User> users;
    public FindAllUsersResponse(String message) {
        super(message);
    }
}
