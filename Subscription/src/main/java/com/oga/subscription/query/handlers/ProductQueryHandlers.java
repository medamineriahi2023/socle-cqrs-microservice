package com.oga.subscription.query.handlers;

import com.oga.subscription.query.rest.dto.Product;
import com.oga.subscription.query.rest.repository.ProductQueryRepository;
import com.oga.cqrsref.domain.BaseEntity;
import com.oga.cqrsref.queries.BaseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * A query handler for handling queries related to products.
 *
 * @since 2023-06-30
 */
@Service
@ComponentScan("com.oga.subscription.query.rest.repository")
@Primary
public class ProductQueryHandlers implements ProductQueryHandler {

    @Autowired
    private ProductQueryRepository productQueryRepository;

    /**
     * Constructs a ProductQueryHandlers instance with the specified ProductQueryRepository.
     *
     * @param productQueryRepository The repository for querying product information.
     */
    public ProductQueryHandlers(ProductQueryRepository productQueryRepository) {
        this.productQueryRepository = productQueryRepository;
    }

    /**
     * Handles the given base query by retrieving product information from the repository.
     *
     * @param baseQuery The base query to handle.
     * @return A list of BaseEntity objects representing the products.
     */
    @Override
    public List<BaseEntity> handle(BaseQuery baseQuery) {
        Iterable<Product> Products = productQueryRepository.findAll();
        List<BaseEntity> ProductsList = new ArrayList<>();
        Products.forEach(ProductsList::add);
        return ProductsList;
    }
}
