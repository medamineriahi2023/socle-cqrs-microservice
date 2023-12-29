package com.oga.subscription.query.events;

import com.oga.subscription.command.events.product.CreateProductEvent;
import com.oga.subscription.command.events.product.DeleteProductEvent;
import com.oga.subscription.command.events.product.UpdateProductEvent;
import com.oga.subscription.query.rest.dto.Product;
import com.oga.subscription.query.rest.repository.ProductQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

/**
 * An event handler for handling create events by saving the product to a MongoDB repository.
 */
@Service
@EnableMongoRepositories("com.oga.subscription.query.rest.repository")
@Slf4j
@Qualifier("IProductEventHandler")
public class ProductEventHandler implements IProductEventHandler {
    @Autowired
    private ProductQueryRepository productQueryRepository;
    /**
     * Handles the create product event by saving the product to the MongoDB repository.
     *
     * @param event The create product event to handle.
     */
    @Override
    public void on(CreateProductEvent event) {
        Product product =new Product() ;
       try{
           product = Product.builder()
                   .id(event.getIdentifier())
                   .category(event.getCategory())
                   .name(event.getName())
                   .type(event.getType())
                   .build();

       }catch (NumberFormatException e) {
           throw new IllegalArgumentException(e);
       }

        productQueryRepository.save(product);
    }

    /**
     * Handles the update product event by updating the product in the MongoDB repository.
     *
     * @param event The update product event to handle.
     */
    @Override
    public void on(UpdateProductEvent event) {
        productQueryRepository.findById(event.getIdentifier()).ifPresent(product1 -> {
            try {
            product1.setName(event.getName());
            product1.setType(event.getType());
            product1.setCategory(event.getCategory());
            productQueryRepository.save(product1);
            } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Id non valide", e);
            }

        });
    }

    /**
     * Handles the delete product event by deleting the product from the MongoDB repository.
     *
     * @param event The delete product event to handle.
     */
    @Override
    public void on(DeleteProductEvent event) {
        productQueryRepository.findById(event.getIdentifier()).ifPresent(product -> {
            productQueryRepository.delete(product);
        });

    }


}
