package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.config.PayHereConfig;
import com.busfleetmanagement.system.dto.PayHerePaymentResponse;
import com.busfleetmanagement.system.entity.Booking;
import com.busfleetmanagement.system.entity.Payment;
import com.busfleetmanagement.system.enums.BookingStatus;
import com.busfleetmanagement.system.enums.PaymentStatus;
import com.busfleetmanagement.system.repository.BookingRepository;
import com.busfleetmanagement.system.repository.PaymentRepository;
import com.busfleetmanagement.system.util.PayHereHashUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Optional;

@Service
public class PayHereService {

    private final PayHereConfig payHereConfig;
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;

    public PayHereService(
            PayHereConfig payHereConfig,
            BookingRepository bookingRepository,
            PaymentRepository paymentRepository) {

        this.payHereConfig = payHereConfig;
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
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

        Payment paymentRecord = paymentRepository
                .findPaymentByBookingId(bookingId)
                .orElseThrow(() ->
                        new RuntimeException("Payment record not found"));


        if (booking.getStatus() != BookingStatus.PAYMENT_PENDING) {
            throw new RuntimeException("Booking is not waiting for payment");
        }

        // Disallow invalid payment retry attempts
        if (paymentRecord.getPaymentStatus() != PaymentStatus.PENDING
                && paymentRecord.getPaymentStatus() != PaymentStatus.FAILED) {
            throw new RuntimeException("Payment cannot be retried");
        }

        // Disallow non-card payments
        if (!"Card Payment".equals(paymentRecord.getPaymentMethod())) {
            throw new RuntimeException(
                    "Card payment cannot be made for non-card payment bookings"
            );
        }

        // Reset a failed payment for a new attempt.
        if (paymentRecord.getPaymentStatus() == PaymentStatus.FAILED) {
            paymentRecord.setPaymentStatus(PaymentStatus.PENDING);
            paymentRecord.setTransactionReference(null);
            paymentRepository.save(paymentRecord);
        }

        /*
         * Change this to whatever field you use
         * for the advance/payment amount.
         */
        BigDecimal paymentAmount = paymentRecord.getAmount();

        if (paymentAmount == null || paymentAmount.signum() <= 0) {
            throw new IllegalStateException("Invalid payment amount");
        }

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

        Payment paymentRecord = paymentRepository
                .findPaymentByBookingId(bookingId)
                .orElseThrow(() ->
                        new RuntimeException("Payment record not found"));

        BigDecimal notifiedAmount = new BigDecimal(payHereAmount);

        if (notifiedAmount.compareTo(paymentRecord.getAmount()) != 0) {
            throw new RuntimeException("Payment amount mismatch");
        }

        if (!"LKR".equals(payHereCurrency)) {
            throw new RuntimeException("Payment currency mismatch");
        }

        /*
         * PayHere:
         *
         * 2  = success
         * 0  = pending
         * -1 = cancelled
         * -2 = failed
         * -3 = chargedback
         */

        if (paymentRecord.getPaymentStatus() == PaymentStatus.SUCCESS) {
            // A duplicate successful notification is safe to ignore.
            return;
        }

        switch (statusCode) {

            case "2":

                /*
                 * Payment successful.
                 */

                paymentRecord.setPaymentStatus(PaymentStatus.SUCCESS);
                paymentRecord.setTransactionReference(paymentId);
                booking.setStatus(BookingStatus.CONFIRMED);

                break;

            case "0":
                // Keep the booking waiting for payment.
                break;

            case "-1":
            case "-2":
                paymentRecord.setPaymentStatus(PaymentStatus.FAILED);
                paymentRecord.setTransactionReference(paymentId);
                // Keep the booking PAYMENT_PENDING so the customer can retry.
                break;

            case "-3":
                paymentRecord.setPaymentStatus(PaymentStatus.FAILED);
                paymentRecord.setTransactionReference(paymentId);
                // Apply your chargeback policy to the booking.
                break;


            default:
                throw new RuntimeException(
                        "Unknown PayHere status: " + statusCode
                );
        }

        bookingRepository.save(booking);
        paymentRepository.save(paymentRecord);
    }
}