package com.oga.subscription.command.rest.response;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Represents the response for deleting a product.
 * Extends the {@link BaseResponse} class and adds an ID field to represent the ID of the deleted product.
 * The response includes a message that indicates the status of the request.
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeleteProductResponse extends BaseResponse {
    private String id;
    /**
     * Constructs a new DeleteProductResponse with the specified message and product ID.
     *
     * @param message The response message.
     * @param id      The ID of the deleted product.
     */
    public DeleteProductResponse(String message, String id) {
        super(message);
        this.id = id;
    }
}
