package com.oga.ged.query.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CountFilesByFolderResponse extends BaseResponse{
    private long fileCount;
    public CountFilesByFolderResponse(String message) {
        super(message);
    }
}
