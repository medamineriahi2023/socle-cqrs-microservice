package com.oga.subscription.query.events;

import com.oga.subscription.command.events.catalog.CreatePlanEvent;
import com.oga.subscription.query.rest.dto.Plan;
import com.oga.subscription.query.rest.repository.PlanQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Qualifier("IPlanEventHandler")
public class PlanEventHandler implements IPlanEventHandler {
    @Autowired
    private PlanQueryRepository planQueryRepository;


    @Override
    public void on(CreatePlanEvent event) {
        Plan plan =new Plan() ;
        try{
            plan = Plan.builder()
                    .id(event.getIdentifier())
                    .planId(event.getPlanId())
                    .productName(event.getProductName())
                    .billingPeriod(event.getBillingPeriod())
                    .amount(event.getAmount())
                    .availableBaseProducts(event.getAvailableBaseProducts())
                    .currency(event.getCurrency())
                    .productCategory(event.getProductCategory())
                    .trialLength(event.getTrialLength())
                    .trialTimeUnit(event.getTrialTimeUnit())
                    .build();

        }catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }

        planQueryRepository.save(plan);
    }
}
