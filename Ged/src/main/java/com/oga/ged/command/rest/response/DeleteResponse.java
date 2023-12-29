package com.oga.ged.command.rest.response;

import lombok.Data;

@Data
public class DeleteResponse extends BaseResponse {
    public DeleteResponse(String message) {
        super(message);
    }
}
