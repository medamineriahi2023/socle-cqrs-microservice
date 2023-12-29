package com.oga.ged.command.rest.response;

import lombok.Data;

@Data
public class MoveResponse extends BaseResponse {
    public MoveResponse(String message) {
        super(message);
    }
}
