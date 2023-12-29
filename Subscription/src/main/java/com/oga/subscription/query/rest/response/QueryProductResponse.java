package com.oga.subscription.query.rest.response;

import com.oga.subscription.query.rest.dto.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * A response object for queries that return a list of products.
 */
    @Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class QueryProductResponse extends BaseResponse {

    /**
     * The list of product returned by the query.
     */
    private List<Product> products;

    /**
     * Constructs a new QueryResponse object with a message.
     *
     * @param message the message to include in the response.
     */
    public QueryProductResponse(String message) {
        super(message);
    }
}
