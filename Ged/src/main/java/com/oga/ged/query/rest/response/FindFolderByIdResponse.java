package com.oga.ged.query.rest.response;

import com.oga.ged.query.rest.dto.Folder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FindFolderByIdResponse  extends BaseResponse {
    private Folder folder;
    public FindFolderByIdResponse(String message) {
        super(message);
    }
}
