package com.oga.ged.query.rest.response;

import com.oga.ged.query.rest.dto.File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SearchFilesByMetadataResponse extends BaseResponse {
    private List<File> files;
    public SearchFilesByMetadataResponse(String message) {
        super(message);
    }
}
