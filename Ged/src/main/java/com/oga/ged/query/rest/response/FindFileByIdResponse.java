package com.oga.ged.query.rest.response;

import com.oga.ged.query.rest.dto.File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FindFileByIdResponse  extends BaseResponse {
    private File file;
    public FindFileByIdResponse(String message) {
        super(message);
    }
}
