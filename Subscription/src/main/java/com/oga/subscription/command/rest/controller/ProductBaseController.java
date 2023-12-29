package com.oga.subscription.command.rest.controller;


import com.oga.subscription.command.commands.product.CreateProductCommand;
import com.oga.subscription.command.commands.product.DeleteProductCommand;
import com.oga.subscription.command.commands.product.UpdateProductCommand;
import com.oga.subscription.command.rest.constant.Constant;
import com.oga.subscription.command.rest.response.BaseResponse;
import com.oga.subscription.command.rest.response.CreateProductResponse;
import com.oga.subscription.command.rest.response.DeleteProductResponse;
import com.oga.subscription.command.rest.response.UpdateProductResponse;
import com.oga.cqrsref.infrastructure.CommandDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;



@RestController
@RequestMapping(path = "/api/v1/products")
@Slf4j
public class ProductBaseController {

    private final CommandDispatcher commandDispatcher;
    /**
     * Constructs a ProductBaseController instance.
     *
     * @param commandDispatcher The command dispatcher for sending commands.
     */
    @Autowired
    public ProductBaseController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    /**
     * Handles a request to create a new product.
     *
     * @param command The CreateProductCommand object representing the product to create.
     * @return A ResponseEntity containing a BaseResponse object with a message and the ID of the new product.
     *
     * If the request is successful, a status of {@link HttpStatus#CREATED} is returned.
     * If the request is unsuccessful due to a bad request, a status of {@link HttpStatus#BAD_REQUEST} is returned.
     * If an error occurs during processing, a status of {@link HttpStatus#INTERNAL_SERVER_ERROR} is returned.
     */

    @PostMapping
    public ResponseEntity<BaseResponse> createProduct(@RequestBody CreateProductCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        commandDispatcher.send(command);
        return new ResponseEntity<>(new CreateProductResponse(Constant.SUCCESS_MESSAGE, command.getId()), HttpStatus.CREATED);
    }
    /**
     * Handles a request to update a product.
     *
     * @param command    The UpdateProductCommand object representing the product to update.
     * @param idProduct  The ID of the product to update.
     * @return A ResponseEntity containing a BaseResponse object with a message and the ID of the updated product.
     *
     * If the request is successful, a status of {@link HttpStatus#OK} is returned.
     * If the request is unsuccessful due to a bad request, a status of {@link HttpStatus#BAD_REQUEST} is returned.
     * If an error occurs during processing, a status of {@link HttpStatus#INTERNAL_SERVER_ERROR} is returned.
     */
    @PutMapping
    public ResponseEntity<BaseResponse> updateProduct(@RequestBody UpdateProductCommand command, @RequestParam String idProduct) {
        command.setId(idProduct);
        commandDispatcher.send(command);
        return new ResponseEntity<>(new UpdateProductResponse(Constant.SUCCESS_MESSAGE, idProduct), HttpStatus.OK);
    }
    /**
     * Handles a request to delete a product.
     *
     * @param idProduct The ID of the product to delete.
     * @return A ResponseEntity containing a BaseResponse object with a message and the ID of the deleted product.
     *
     * If the request is successful, a status of {@link HttpStatus#OK} is returned.
     * If the request is unsuccessful due to a bad request, a status of {@link HttpStatus#BAD_REQUEST} is returned.
     * If an error occurs during processing, a status of {@link HttpStatus#INTERNAL_SERVER_ERROR} is returned.
     */
    @DeleteMapping
    public ResponseEntity<BaseResponse> deleteProduct(@RequestParam String idProduct) {
        DeleteProductCommand command = new DeleteProductCommand();
        command.setId(idProduct);
        commandDispatcher.send(command);
        return new ResponseEntity<>(new DeleteProductResponse(Constant.SUCCESS_MESSAGE, idProduct), HttpStatus.OK);
    }


}
