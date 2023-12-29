package com.oga.cqrs.command.rest.response;

import com.oga.cqrsref.Response.BaseResponse;
import lombok.Data;


@Data
public class BasicResponse extends BaseResponse {
  private String id;
  public BasicResponse(String message, String id) {
    super(message);
    this.id = id;
  }



}
