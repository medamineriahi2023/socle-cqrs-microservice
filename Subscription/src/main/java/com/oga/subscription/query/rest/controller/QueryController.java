package com.oga.subscription.query.rest.controller;

import com.oga.cqrsref.infrastructure.IQueryDispatcher;
import com.oga.subscription.command.rest.constant.Constant;
import com.oga.subscription.query.queries.FindAllPlans;
import com.oga.subscription.query.queries.FindAllProducts;
import com.oga.subscription.query.rest.dto.Plan;
import com.oga.subscription.query.rest.dto.Product;
import com.oga.subscription.query.rest.response.QueryPlanResponse;
import com.oga.subscription.query.rest.response.QueryProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

/**
 * Controller for handling HTTP requests related to queries.
 */
@RestController
public class QueryController {

    @Autowired
    private IQueryDispatcher queryDispatcher;

    /**
     * Handles HTTP GET requests to retrieve all products.
     *
     * @return the HTTP response containing a list of all products.
     */
    @GetMapping(path = "/products")
    public ResponseEntity<QueryProductResponse> getAllProducts() {
        List<Product> products = queryDispatcher.send(new FindAllProducts());
        if ( products == null ) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        var response = QueryProductResponse.builder()
                .products(products)
                .message(MessageFormat.format(Constant.GET_ALL_SUCCESS, products.size()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/products/{id}")
    public ResponseEntity<QueryProductResponse> getProductById(@RequestParam("id") String id) {
        List<Product> products = queryDispatcher.send(new FindAllProducts());
        if ( products == null ) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        products = products.stream()
                .filter(product -> Objects.equals(product.getId(), id)).toList();

        var response = QueryProductResponse.builder()
                .products(products)
                .message(MessageFormat.format(Constant.GET_ALL_SUCCESS, products.size()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /**
     * Handles HTTP GET requests to retrieve all plans.
     *
     * @return the HTTP response containing a list of all products.
     */
    @GetMapping(path = "/plans")
    public ResponseEntity<QueryPlanResponse> getAllPlans() {
        List<Plan> plans = queryDispatcher.send(new FindAllPlans());
        if ( plans == null ) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        var response = QueryPlanResponse.builder()
                .plans(plans)
                .message(MessageFormat.format(Constant.GET_ALL_SUCCESS, plans.size()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
