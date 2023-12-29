package com.oga.subscription.command.rest.response;

public class CreateTenantResponse extends BaseResponse{
    private String id;
    /**
     * Constructs a CreatePlanResponse object with the specified message and product ID.
     *
     * @param message The response message.
     * @param id      The ID of the created product.
     */
    public CreateTenantResponse(String message, String id) {
        super(message);
        this.id = id;
    }
}
