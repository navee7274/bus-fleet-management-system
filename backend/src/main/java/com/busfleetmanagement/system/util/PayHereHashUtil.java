package com.busfleetmanagement.system.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PayHereHashUtil {

    private PayHereHashUtil() {
    }

    public static String md5(String input) {

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] digest =
                    md.digest(input.getBytes(StandardCharsets.UTF_8));

            BigInteger number = new BigInteger(1, digest);

            StringBuilder hash = new StringBuilder(
                    number.toString(16)
            );

            while (hash.length() < 32) {
                hash.insert(0, "0");
            }

            return hash.toString().toUpperCase();

        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }

    public static String generatePaymentHash(
            String merchantId,
            String orderId,
            String amount,
            String currency,
            String merchantSecret
    ) {

        String hashedSecret =
                md5(merchantSecret).toUpperCase();

        String raw =
                merchantId
                        + orderId
                        + amount
                        + currency
                        + hashedSecret;

        return md5(raw).toUpperCase();
    }

    public static String generateNotificationHash(
            String merchantId,
            String orderId,
            String amount,
            String currency,
            String statusCode,
            String merchantSecret
    ) {

        String hashedSecret =
                md5(merchantSecret).toUpperCase();

        String raw =
                merchantId
                        + orderId
                        + amount
                        + currency
                        + statusCode
                        + hashedSecret;

        return md5(raw).toUpperCase();
    }
}