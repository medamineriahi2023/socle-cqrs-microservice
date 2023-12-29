package com.oga.ged.query.rest.response;

import com.oga.ged.query.rest.dto.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FindUserByIdResponse extends BaseResponse{
    private User user;
    public FindUserByIdResponse(String message) {
        super(message);
    }
}
