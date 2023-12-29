package com.oga.subscription.command.commands.account;

import com.oga.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.killbill.billing.catalog.api.Currency;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents a command to create a plan.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountCommand extends BaseCommand {
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
