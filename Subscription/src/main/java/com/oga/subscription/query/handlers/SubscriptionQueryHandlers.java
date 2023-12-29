package com.oga.subscription.query.handlers;

import com.oga.cqrsref.domain.BaseEntity;
import com.oga.cqrsref.queries.BaseQuery;
import com.oga.subscription.query.rest.dto.Subscription;
import com.oga.subscription.query.rest.repository.SubscriptionQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class SubscriptionQueryHandlers implements SubscriptionQueryHandler {

    @Autowired
    private SubscriptionQueryRepository subscriptionQueryRepository;

    /**
     * Constructs a SubscriptionQueryHandlers instance with the specified SubscriptionQueryRepository.
     *
     * @param subscriptionQueryRepository The repository for querying product information.
     */
    public SubscriptionQueryHandlers(SubscriptionQueryRepository subscriptionQueryRepository) {
        this.subscriptionQueryRepository = subscriptionQueryRepository;
    }

    /**
     * Handles the given base query by retrieving product information from the repository.
     *
     * @param baseQuery The base query to handle.
     * @return A list of BaseEntity objects representing the products.
     */
    @Override
    public List<BaseEntity> handle(BaseQuery baseQuery) {
        Iterable<Subscription> subscriptions = subscriptionQueryRepository.findAll();
        subscriptions.forEach(s -> System.out.println(s));
        List<BaseEntity> subscriptionList = new ArrayList<>();
        subscriptions.forEach(subscriptionList::add);
        return subscriptionList;
    }
}
