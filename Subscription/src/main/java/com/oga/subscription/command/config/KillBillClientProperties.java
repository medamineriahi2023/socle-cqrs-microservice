package com.oga.subscription.command.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.killbill.billing.client.KillBillHttpClient;
import org.killbill.billing.client.RequestOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KillBillClientProperties {
    @Value("${killbill.host}")
    private String host;
    @Value("${killbill.username}")
    private String username;
    @Value("${killbill.password}")
    private String password;
    @Value("${killbill.apiKey}")
    private String apiKey;
    @Value("${killbill.apiSecret}")
    private String apiSecret;

    private static KillBillHttpClient killBillHttpClient = null;
    private static RequestOptions requestOptions = null;

    public KillBillHttpClient getHttpClient() {
        if (killBillHttpClient == null) {
            return new KillBillHttpClient(host, username, password, apiKey, apiSecret,
                    null,
                    1000,
                    5000,
                    5000);
        }
        return killBillHttpClient;
    }

    public RequestOptions getRequestOptions() {
        if (requestOptions == null) {
            return RequestOptions.builder()
                    .withCreatedBy(username).withUser(username).withPassword(password)
                    .withTenantApiKey(apiKey)
                    .withTenantApiSecret(apiSecret)
                    .build();
        }
        return requestOptions;
    }

}
