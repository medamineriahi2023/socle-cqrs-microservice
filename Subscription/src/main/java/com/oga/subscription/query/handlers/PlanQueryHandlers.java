package com.oga.subscription.query.handlers;

import com.oga.cqrsref.domain.BaseEntity;
import com.oga.cqrsref.queries.BaseQuery;
import com.oga.subscription.query.rest.dto.Plan;
import com.oga.subscription.query.rest.repository.PlanQueryRepository;
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
public class PlanQueryHandlers implements PlanQueryHandler {

    @Autowired
    private PlanQueryRepository planQueryRepository;

    /**
     * Constructs a PlanQueryHandlers instance with the specified PlanQueryRepository.
     *
     * @param planQueryRepository The repository for querying product information.
     */
    public PlanQueryHandlers(PlanQueryRepository planQueryRepository) {
        this.planQueryRepository = planQueryRepository;
    }

    /**
     * Handles the given base query by retrieving product information from the repository.
     *
     * @param baseQuery The base query to handle.
     * @return A list of BaseEntity objects representing the products.
     */
    @Override
    public List<BaseEntity> handle(BaseQuery baseQuery) {
        Iterable<Plan> plans = planQueryRepository.findAll();
        List<BaseEntity> planList = new ArrayList<>();
        plans.forEach(planList::add);
        return planList;
    }
}
