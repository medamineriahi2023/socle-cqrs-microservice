package com.oga.subscription.query.events;

import com.oga.subscription.command.events.subscription.CreateSubscriptionEvent;
import com.oga.subscription.query.rest.dto.Subscription;
import com.oga.subscription.query.rest.repository.SubscriptionQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Qualifier("ISubscriptionEventHandler")
public class SubscriptionEventHandler implements ISubscriptionEventHandler {
    @Autowired
    private SubscriptionQueryRepository subscriptionQueryRepository;


    @Override
    public void on(CreateSubscriptionEvent event) {
        Subscription subscription =new Subscription() ;
        try{
            subscription = Subscription.builder()
                    .id(event.getIdentifier())
                    .accountId(event.getAccountId())
                    .quantity(event.getQuantity())
                    .billingPeriod(event.getBillingPeriod())
                    .billCycleDayLocal(event.getBillCycleDayLocal())
                    .billingEndDate(event.getBillingEndDate())
                    .billingStartDate(event.getBillingStartDate())
                    .bundleExternalKey(event.getBundleExternalKey())
                    .bundleId(event.getBundleId())
                    .cancelledDate(event.getCancelledDate())
                    .subscriptionId(event.getSubscriptionId())
                    .chargedThroughDate(event.getChargedThroughDate())
                    .productCategory(event.getProductCategory())
                    .externalKey(event.getExternalKey())
                    .productName(event.getProductName())
                    .phaseType(event.getPhaseType())
                    .planName(event.getPlanName())
                    .state(event.getState())
                    .priceOverrides(event.getPriceOverrides())
                    .prices(event.getPrices())
                    .sourceType(event.getSourceType())
                    .startDate(event.getStartDate())
                    .priceList(event.getPriceList())
                    .events(event.getEvents())
                    .build();

        }catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }

        subscriptionQueryRepository.save(subscription);
    }
}
