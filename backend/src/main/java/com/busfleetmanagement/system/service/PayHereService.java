package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.config.PayHereConfig;
import com.busfleetmanagement.system.dto.PayHerePaymentResponse;
import com.busfleetmanagement.system.entity.Booking;
import com.busfleetmanagement.system.enums.BookingStatus;
import com.busfleetmanagement.system.repository.BookingRepository;
import com.busfleetmanagement.system.util.PayHereHashUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class PayHereService {

    private final PayHereConfig payHereConfig;
    private final BookingRepository bookingRepository;

    public PayHereService(
            PayHereConfig payHereConfig,
            BookingRepository bookingRepository
    ) {
        this.payHereConfig = payHereConfig;
        this.bookingRepository = bookingRepository;
    }

    /**
     * Generate payment information for a booking.
     */
    public PayHerePaymentResponse createPayment(int bookingId) {

        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found")
                );

        if (booking.getStatus() != BookingStatus.PAYMENT_PENDING) {
            throw new RuntimeException(
                    "Booking is not waiting for payment"
            );
        }

        /*
         * Change this to whatever field you use
         * for the advance/payment amount.
         */
        BigDecimal paymentAmount = booking.getAdvanceAmount();

        String amount = paymentAmount
                .setScale(2, RoundingMode.HALF_UP)
                .toPlainString();

        String currency = "LKR";

        String orderId = "BOOKING-" + booking.getBookingID();

        String hash = PayHereHashUtil.generatePaymentHash(
                payHereConfig.getMerchantId(),
                orderId,
                amount,
                currency,
                payHereConfig.getMerchantSecret()
        );

        PayHerePaymentResponse response =
                new PayHerePaymentResponse();

        response.setMerchantId(
                payHereConfig.getMerchantId()
        );

        response.setOrderId(orderId);

        response.setAmount(amount);

        response.setCurrency(currency);

        response.setHash(hash);

        response.setCheckoutUrl(
                payHereConfig.getCheckoutUrl()
        );

        response.setReturnUrl(
                payHereConfig.getReturnUrl()
        );

        response.setCancelUrl(
                payHereConfig.getCancelUrl()
        );

        response.setNotifyUrl(
                payHereConfig.getNotifyUrl()
        );

        /*
         * Replace these with your actual Booking fields.
         */
        response.setFirstName(
                booking.getCustomerFirstName()
        );

        response.setLastName(
                booking.getCustomerLastName()
        );

        response.setEmail(
                booking.getCustomerContactEmail()
        );

        response.setPhone(
                booking.getCustomerContactPhone()
        );

        response.setAddress(
                booking.getCustomerAddress()
        );

        response.setCity(
                booking.getCustomerCity()
        );

        response.setCountry("Sri Lanka");

        response.setItems(
                "Bus Booking #" + booking.getBookingID()
        );

        return response;
    }


    /**
     * Handle PayHere server notification.
     */
    @Transactional
    public void handleNotification(
            String merchantId,
            String orderId,
            String paymentId,
            String payHereAmount,
            String payHereCurrency,
            String statusCode,
            String md5sig
    ) {

        /*
         * First make sure the notification belongs
         * to our PayHere merchant account.
         */
        if (!payHereConfig
                .getMerchantId()
                .equals(merchantId)) {

            throw new RuntimeException(
                    "Invalid merchant ID"
            );
        }

        /*
         * Generate our own checksum.
         */
        String localMd5Sig =
                PayHereHashUtil.generateNotificationHash(
                        merchantId,
                        orderId,
                        payHereAmount,
                        payHereCurrency,
                        statusCode,
                        payHereConfig.getMerchantSecret()
                );

        /*
         * NEVER process an unverified notification.
         */
        if (!localMd5Sig.equalsIgnoreCase(md5sig)) {

            throw new RuntimeException(
                    "Invalid PayHere signature"
            );
        }

        /*
         * Convert:
         *
         * BOOKING-123
         *
         * into:
         *
         * 123
         */
        if (!orderId.startsWith("BOOKING-")) {
            throw new RuntimeException(
                    "Invalid order ID"
            );
        }

        int bookingId;

        try {

            bookingId = Integer.parseInt(
                    orderId.substring("BOOKING-".length())
            );

        } catch (NumberFormatException e) {

            throw new RuntimeException(
                    "Invalid booking order ID"
            );
        }

        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Booking not found"
                        )
                );

        /*
         * PayHere:
         *
         * 2  = success
         * 0  = pending
         * -1 = cancelled
         * -2 = failed
         * -3 = chargedback
         */
        switch (statusCode) {

            case "2":

                /*
                 * Payment successful.
                 */
                booking.setStatus(
                        BookingStatus.CONFIRMED
                );

                break;

            case "0":

                /*
                 * Payment pending.
                 */
                booking.setStatus(
                        BookingStatus.PAYMENT_PENDING
                );

                break;

            case "-1":
            case "-2":
            case "-3":

                /*
                 * Payment failed/cancelled.
                 */
                booking.setStatus(
                        BookingStatus.REJECTED
                );

                break;

            default:

                throw new RuntimeException(
                        "Unknown PayHere status: " + statusCode
                );
        }

        bookingRepository.save(booking);
    }
}