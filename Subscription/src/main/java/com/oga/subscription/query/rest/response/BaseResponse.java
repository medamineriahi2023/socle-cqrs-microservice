package com.oga.subscription.query.rest.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * A base response class for other response classes to inherit from. Contains a message field that can be used to hold a
 * message describing the result of an operation.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
    private String message;
}