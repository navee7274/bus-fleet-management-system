package com.busfleetmanagement.system.controller;

import com.busfleetmanagement.system.dto.PayHerePaymentResponse;
import com.busfleetmanagement.system.entity.Payment;
import com.busfleetmanagement.system.enums.PaymentStatus;
import com.busfleetmanagement.system.exception.BadRequestException;
import com.busfleetmanagement.system.exception.ResourceNotFoundException;
import com.busfleetmanagement.system.service.PayHereService;

import com.busfleetmanagement.system.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PayHereService payHereService;
    private final PaymentService paymentService;

    public PaymentController(PayHereService payHereService, PaymentService paymentService) {
        this.payHereService = payHereService;
        this.paymentService = paymentService;
    }

    // Generate payment payload for a booking
    @GetMapping("/{bookingId}")
    public ResponseEntity<PayHerePaymentResponse> createPayment(
            @PathVariable int bookingId) {

        PayHerePaymentResponse response =
                payHereService.createPayment(bookingId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{bookingId}/paymentmethod")
    public ResponseEntity<Map<String, String>> getPaymentMethod(
            @PathVariable int bookingId) {

        Payment payment = paymentService
                .getPaymentRecordByBookingId(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found")
                );

        return ResponseEntity.ok(
                Map.of(
                        "paymentMethod", payment.getPaymentMethod(),
                        "paymentStatus", payment.getPaymentStatus().name()
                )
        );
    }

    @PostMapping("/{bookingId}/mark")
    public ResponseEntity<String> markPaymentDone(
            @PathVariable int bookingId) {

        Payment payment = paymentService
                .getPaymentRecordByBookingId(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found")
                );

        paymentService.markPaymentDone(payment);

        return ResponseEntity.ok("OK");
    }

    // Receive payment notification from PayHere
    @PostMapping("/notify")
    public ResponseEntity<String> payhereNotify(
            @RequestParam("merchant_id") String merchantId,
            @RequestParam("order_id") String orderId,
            @RequestParam("payment_id") String paymentId,
            @RequestParam("payhere_amount") String payHereAmount,
            @RequestParam("payhere_currency") String payHereCurrency,
            @RequestParam("status_code") String statusCode,
            @RequestParam("md5sig") String md5sig) {

        payHereService.handleNotification(
                merchantId,
                orderId,
                paymentId,
                payHereAmount,
                payHereCurrency,
                statusCode,
                md5sig
        );

        return ResponseEntity.ok("OK");
    }
}