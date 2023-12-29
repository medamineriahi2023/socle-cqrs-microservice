package com.oga.subscription.command.rest.response;
import lombok.Data;
/**
 * Represents the response for updating a product.
 * Extends the {@link BaseResponse} class and adds an ID field to represent the ID of the updated product.
 * The response includes a message that indicates the status of the request.
 *
 */

@Data
public class UpdateProductResponse extends BaseResponse {
    private String id;
    /**
     * Constructs a new UpdateProductResponse with the specified message and product ID.
     *
     * @param message The response message.
     * @param id      The ID of the updated product.
     */
    public UpdateProductResponse(String message, String id) {
        super(message);
        this.id = id;
    }
}
