package com.oga.subscription.command.events.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oga.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.joda.time.DateTime;
import org.killbill.billing.catalog.api.Currency;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonSerialize
public class CreateAccountEvent extends BaseEvent {
    private String identifier;
    private UUID accountId;
    private String name;
    private Integer firstNameLength;
    private String externalKey;
    private String email;
    private Integer billCycleDayLocal;
    private Currency currency;
    private UUID parentAccountId;
    private Boolean isPaymentDelegatedToParent;
    private UUID paymentMethodId;
    private DateTime referenceTime;
    private String timeZone;
    private String address1;
    private String address2;
    private String postalCode;
    private String company;
    private String city;
    private String state;
    private String country;
    private String locale;
    private String phone;
    private String notes;
    private Boolean isMigrated;
    private BigDecimal accountBalance;
    private BigDecimal accountCBA;

}
