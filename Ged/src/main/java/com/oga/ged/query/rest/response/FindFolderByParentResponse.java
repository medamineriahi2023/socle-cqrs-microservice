package com.oga.ged.query.rest.response;

import com.oga.ged.query.rest.dto.Folder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FindFolderByParentResponse extends BaseResponse {
    private List<Folder> folders;
    public FindFolderByParentResponse(String message) {
        super(message);
    }
}
