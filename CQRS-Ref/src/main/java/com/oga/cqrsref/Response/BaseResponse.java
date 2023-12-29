package com.oga.cqrsref.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * This class represents the response object for REST endpoints.
 *  It contains a message attribute that provides additional information about the response.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
  private String message;
}
