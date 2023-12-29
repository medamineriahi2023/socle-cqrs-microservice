package com.oga.subscription.command.rest.repository;




import com.oga.subscription.command.rest.dto.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for accessing product data in a JPA-based data store.
 */
@Qualifier
public  interface JpaRepos extends JpaRepository<Product, Long> {
    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product.
     * @return An Optional containing the product with the specified ID, or an empty Optional if not found.
     */
    Optional<Product> findById(String id);
}
