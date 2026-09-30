package com.busfleetmanagement.system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayHereConfig {

    @Value("${payhere.merchant-id}")
    private String merchantId;

    @Value("${payhere.merchant-secret}")
    private String merchantSecret;

    @Value("${payhere.return-url}")
    private String checkoutUrl;

    @Value("${payhere.return-url}")
    private String returnUrl;

    @Value("${payhere.cancel-url}")
    private String cancelUrl;

    @Value("${payhere.notify-url}")
    private String notifyUrl;

    public String getMerchantId() {
        return merchantId;
    }

    public String getMerchantSecret() {
        return merchantSecret;
    }

    public String getCheckoutUrl() {
        return checkoutUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }
}