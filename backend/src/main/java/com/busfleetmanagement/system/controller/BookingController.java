package com.busfleetmanagement.system.controller;

import com.busfleetmanagement.system.dto.BookingRequest;
import com.busfleetmanagement.system.dto.BookingResponse;
import com.busfleetmanagement.system.entity.Booking;
import com.busfleetmanagement.system.enums.BookingStatus;
import com.busfleetmanagement.system.exception.ResourceNotFoundException;
import com.busfleetmanagement.system.service.AvailabilityService;
import com.busfleetmanagement.system.service.BookingService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;
    private final AvailabilityService availabilityService;

    public BookingController(BookingService bookingService, AvailabilityService availabilityService) {
        this.bookingService = bookingService;
        this.availabilityService = availabilityService;
    }

    private BookingResponse toResponse(Booking booking) {

        BookingResponse response = new BookingResponse();

        response.setBookingID(booking.getBookingID());
        response.setCustomerName(booking.getCustomerName());
        response.setCustomerContact(booking.getCustomerContact());
        response.setStartDateTime(booking.getStartDateTime());
        response.setEndDateTime(booking.getEndDateTime());
        response.setStartLocation(booking.getStartLocation());
        response.setDestination(booking.getDestination());
        response.setPassengerCount(booking.getPassengerCount());

        if (booking.getBus() != null) {
            response.setBusRegistrationNo(
                    booking.getBus().getBusRegistrationNo()
            );
        }

        if (booking.getDriver() != null) {
            response.setDriverID(
                    booking.getDriver().getDriverID()
            );
        }

        response.setEstimatedCost(booking.getEstimatedCost());
        response.setFinalPrice(booking.getFinalPrice());
        response.setStatus(booking.getStatus());
        response.setCreatedAt(booking.getCreatedAt());

        if (booking.getJourney() != null) {
            response.setJourneyID(
                    booking.getJourney().getJourneyID()
            );
        }

        return response;
    }

    private Optional<BookingResponse> toResponse(Optional<Booking> booking) {
        return booking.map(this::toResponse);
    }

    private List<BookingResponse> toResponse(List<Booking> bookings) {
        return bookings.stream()
                .map(this::toResponse)
                .toList();
    }


    // =========================================================
    // CUSTOMER
    // =========================================================

    /**
     * Create a temporary booking request.
     *
     * Customer does NOT need to be logged in.
     */
    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @Valid @RequestBody BookingRequest request) {

        Booking booking = new Booking();

        booking.setCustomerName(request.getCustomerName());
        booking.setCustomerContact(request.getCustomerContact());
        booking.setStartDateTime(request.getStartDateTime());
        booking.setEndDateTime(request.getEndDateTime());
        booking.setStartLocation(request.getStartLocation());
        booking.setDestination(request.getDestination());
        booking.setPassengerCount(request.getPassengerCount());

        // Initial booking state
        booking.setStatus(BookingStatus.PENDING);

        // Set this according to your cost calculation
        booking.setEstimatedCost(BigDecimal.ZERO);

        // Until owner sets the price
        booking.setFinalPrice(BigDecimal.ZERO);

        booking.setCreatedAt(LocalDateTime.now());

        Booking savedBooking = bookingService.createBooking(booking);

        BookingResponse response = new BookingResponse();

        response.setBookingID(savedBooking.getBookingID());
        response.setCustomerName(savedBooking.getCustomerName());
        response.setCustomerContact(savedBooking.getCustomerContact());
        response.setStartDateTime(savedBooking.getStartDateTime());
        response.setEndDateTime(savedBooking.getEndDateTime());
        response.setStartLocation(savedBooking.getStartLocation());
        response.setDestination(savedBooking.getDestination());
        response.setPassengerCount(savedBooking.getPassengerCount());
        response.setEstimatedCost(savedBooking.getEstimatedCost());
        response.setFinalPrice(savedBooking.getFinalPrice());
        response.setStatus(savedBooking.getStatus());
        response.setCreatedAt(savedBooking.getCreatedAt());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    /**
     * Get booking details using booking ID.
     */
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBooking(
            @PathVariable Integer bookingId) {



        return ResponseEntity.ok(
                toResponse(bookingService.getBooking(bookingId)
                        .orElseThrow(()-> new ResourceNotFoundException("Booking not found")))
        );
    }


    /**
     * Check which buses are available for the requested period.
     *
     * This is used by the frontend before creating/confirming
     * a booking.
     */
    @GetMapping("/available-buses")
    public ResponseEntity<?> getAvailableBuses(
            @RequestParam LocalDateTime startDateTime,
            @RequestParam LocalDateTime endDateTime,
            @RequestParam String startLocation,
            @RequestParam String destination,
            @RequestParam int passengerCount) {

        return ResponseEntity.ok(
                availabilityService.getAvailableBuses(
                        startDateTime,
                        endDateTime,
                        passengerCount
                )
        );
    }


    /**
     * Customer makes the advance payment for a booking.
     *
     * Usually the frontend would call this after the owner
     * has enabled payment.
     */
    @PostMapping("/{bookingId}/payment")
    public ResponseEntity<?> makePayment(
            @PathVariable Integer bookingId) {

        return ResponseEntity.ok(
                bookingService.makePayment(bookingId)
        );
    }


    // =========================================================
    // OWNER
    // =========================================================

    /**
     * Get all pending booking requests.
     */
    @GetMapping("/pending")
    public ResponseEntity<List<BookingResponse>> getPendingBookings() {

        return ResponseEntity.ok(
                toResponse(bookingService.getPendingBookings())
        );
    }


    /**
     * Owner views a particular booking.
     */
    @GetMapping("/owner/{bookingId}")
    public ResponseEntity<BookingResponse> getBookingForOwner(
            @PathVariable Integer bookingId) {

        return ResponseEntity.ok(
                toResponse(bookingService.getBooking(bookingId))
                        .orElseThrow(()-> new ResourceNotFoundException("Booking not found"))
        );
    }


    /**
     * Owner selects a bus and sets the final price.
     *
     * This also enables payment for the customer.
     */
    @PutMapping("/{bookingId}/price")
    public ResponseEntity<BookingResponse> setBookingPrice(
            @PathVariable Integer bookingId,
            @RequestParam BigDecimal price) {

        return ResponseEntity.ok(
                toResponse(bookingService.setBookingPrice(bookingId, price))
        );
    }

    @PutMapping("/{bookingId}/bus")
    public ResponseEntity<BookingResponse> setBookingBus(
            @PathVariable Integer bookingId,
            @RequestParam String busRegistrationNo) {

        return ResponseEntity.ok(
                toResponse(bookingService.assignBus(bookingId, busRegistrationNo))
        );
    }


    @PutMapping("/{bookingId}/driver")
    public ResponseEntity<BookingResponse> setBookingPrice(
            @PathVariable Integer bookingId,
            @RequestParam String busRegistrationNo,
            @RequestParam double price) {

        return ResponseEntity.ok(
                toResponse(bookingService.assignDriver(
                        bookingId,
                        busRegistrationNo
                ))
        );
    }

    /**
     * Owner confirms the booking after customer payment.
     *
     * This is where the BookingService can create the actual Journey.
     */
    @PostMapping("/{bookingId}/confirm")
    public ResponseEntity<BookingResponse> confirmBooking(
            @PathVariable Integer bookingId) {

        return ResponseEntity.ok(
                toResponse(bookingService.confirmBooking(bookingId))
        );
    }


    /**
     * Owner rejects a booking request.
     */
    @PostMapping("/{bookingId}/reject")
    public ResponseEntity<BookingResponse> rejectBooking(
            @PathVariable Integer bookingId) {

        return ResponseEntity.ok(
                toResponse(bookingService.rejectBooking(bookingId))
        );
    }

    /**
     * Cancel a booking.
     */
    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable Integer bookingId) {

        return ResponseEntity.ok(
                toResponse(bookingService.cancelBooking(bookingId))
        );
    }
}