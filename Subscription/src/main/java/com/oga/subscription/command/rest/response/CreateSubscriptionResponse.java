package com.oga.subscription.command.rest.response;
import lombok.Data;


/**
 * Represents the response for creating a product.
 */
@Data
public class CreateSubscriptionResponse extends BaseResponse {
    private String id;
    /**
     * Constructs a CreateSubscriptionResponse object with the specified message and product ID.
     *
     * @param message The response message.
     * @param id      The ID of the created product.
     */
    public CreateSubscriptionResponse(String message, String id) {
        super(message);
        this.id = id;
    }
}
