package com.oga.subscription.query.handlers;

import com.oga.subscription.query.rest.dto.Product;
import com.oga.subscription.query.rest.repository.ProductQueryRepository;
import com.oga.cqrsref.domain.BaseEntity;
import com.oga.cqrsref.queries.BaseQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.killbill.billing.catalog.api.ProductCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    private Logger logger = LoggerFactory.getLogger(ProductControllerTest.class);

    private ProductQueryHandlers queryHandlers;
    private ProductQueryRepository productQueryRepository;

    @BeforeEach
    void setUp() {
        productQueryRepository = mock(ProductQueryRepository.class);
        queryHandlers = new ProductQueryHandlers(productQueryRepository);


    }

    @Test
    void handle_BaseQuery_ReturnsListOfProducts() {
        // Arrange
        Product product1 = new Product(UUID.randomUUID().toString(), "music", ProductCategory.BASE, "Spotify");
        Product product2 = new Product(UUID.randomUUID().toString(), "movie", ProductCategory.BASE, "Netflix");
        List<Product> products = List.of(product1, product2);

        when(productQueryRepository.findAll()).thenReturn(products);

        // Act
        List<BaseEntity> result = queryHandlers.handle(new BaseQuery());

        // Assert
        assertEquals(2, result.size());
        Iterator<BaseEntity> iterator = result.iterator();
        assertEquals(product1, iterator.next());
        assertEquals(product2, iterator.next());

        // Verify that the repository method was called
        verify(productQueryRepository, times(1)).findAll();
    }

}
