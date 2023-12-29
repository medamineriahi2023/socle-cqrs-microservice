package com.oga.ged.command.rest.response;

import lombok.Data;

@Data
public class UpdateResponse extends BaseResponse {
    public UpdateResponse(String message) {
        super(message);
    }
}
