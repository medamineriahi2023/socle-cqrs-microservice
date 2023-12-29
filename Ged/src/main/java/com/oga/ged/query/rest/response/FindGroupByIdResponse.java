package com.oga.ged.query.rest.response;

import com.oga.ged.query.rest.dto.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FindGroupByIdResponse extends BaseResponse{
    private Group group;
    public FindGroupByIdResponse(String message) {
        super(message);
    }
}
