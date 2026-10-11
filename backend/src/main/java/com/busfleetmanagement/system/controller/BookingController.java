package com.busfleetmanagement.system.controller;

import com.busfleetmanagement.system.dto.BookingRequest;
import com.busfleetmanagement.system.dto.BookingResponse;
import com.busfleetmanagement.system.entity.Booking;
import com.busfleetmanagement.system.entity.Bus;
import com.busfleetmanagement.system.enums.BookingStatus;
import com.busfleetmanagement.system.exception.ResourceNotFoundException;
import com.busfleetmanagement.system.service.AvailabilityService;
import com.busfleetmanagement.system.service.BookingService;

import com.busfleetmanagement.system.service.BusService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;
    private final AvailabilityService availabilityService;
    private final BusService busService;

    public BookingController(
            BookingService bookingService,
            AvailabilityService availabilityService, BusService busService
    ) {
        this.bookingService = bookingService;
        this.availabilityService = availabilityService;
        this.busService = busService;
    }


    // =========================================================
    // RESPONSE MAPPER
    // =========================================================

    private BookingResponse toResponse(Booking booking) {

        BookingResponse response = new BookingResponse();

        response.setBookingID(booking.getBookingID());

        // Customer Details
        response.setCustomerFirstName(booking.getCustomerFirstName());
        response.setCustomerLastName(booking.getCustomerLastName());
        response.setCustomerContactPhone(booking.getCustomerContactPhone());
        response.setCustomerContactEmail(booking.getCustomerContactEmail());
        response.setCustomerAddress(booking.getCustomerAddress());
        response.setCustomerCity(booking.getCustomerCity());

        // Booking Details
        response.setStartDateTime(booking.getStartDateTime());
        response.setEndDateTime(booking.getEndDateTime());
        response.setStartLocation(booking.getStartLocation());
        response.setDestination(booking.getDestination());
        response.setPassengerCount(booking.getPassengerCount());

        // Bus
        if (booking.getBus() != null) {
            response.setBusRegistrationNo(
                    booking.getBus().getBusRegistrationNo()
            );
        }

        // Driver
        if (booking.getDriver() != null) {
            response.setDriverID(
                    booking.getDriver().getDriverID()
            );
        }

        // Pricing
        response.setEstimatedCost(booking.getEstimatedCost());
        response.setFinalPrice(booking.getFinalPrice());
        response.setAdvanceAmount(booking.getAdvanceAmount());

        // Status & creation time
        response.setStatus(booking.getStatus());
        response.setCreatedAt(booking.getCreatedAt());

        // Journey
        if (booking.getJourney() != null) {
            response.setJourneyID(
                    booking.getJourney().getJourneyID()
            );
        }

        return response;
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
     * Customer does NOT need to be logged in.
     */
    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @Valid @RequestBody BookingRequest request) {

        Booking booking = new Booking();

        // =========================
        // Customer Details
        // =========================
        booking.setCustomerFirstName(request.getCustomerFirstName());
        booking.setCustomerLastName(request.getCustomerLastName());
        booking.setCustomerContactPhone(request.getCustomerContactPhone());
        booking.setCustomerContactEmail(request.getCustomerContactEmail());
        booking.setCustomerAddress(request.getCustomerAddress());
        booking.setCustomerCity(request.getCustomerCity());

        // =========================
        // Booking Details
        // =========================
        booking.setStartDateTime(request.getStartDateTime());
        booking.setEndDateTime(request.getEndDateTime());
        booking.setStartLocation(request.getStartLocation());
        booking.setDestination(request.getDestination());
        booking.setPassengerCount(request.getPassengerCount());

        // =========================
        // Bus
        // =========================
        Bus bus = busService.getBusById(
                request.getBusRegistrationNo()
        ).orElseThrow(()-> new ResourceNotFoundException("Bus not found"));

        booking.setBus(bus);

        // Driver is NOT assigned when booking is created
        booking.setDriver(null);

        // =========================
        // Initial Booking State
        // =========================
        booking.setStatus(BookingStatus.PENDING);

        // =========================
        // Initial Pricing
        // =========================
        booking.setEstimatedCost(BigDecimal.ZERO);
        booking.setFinalPrice(null);

        // =========================
        // Created Timestamp
        // =========================
        booking.setCreatedAt(LocalDateTime.now());

        // AdvanceAmount is already initialized
        // to 8000 in the Booking entity

        Booking savedBooking = bookingService.createBooking(booking);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(savedBooking));
    }


    /**
     * Get booking details using booking ID.
     */
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBooking(
            @PathVariable Integer bookingId) {

        Booking booking = bookingService.getBooking(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found")
                );

        return ResponseEntity.ok(toResponse(booking));
    }


    /**
     * Update customer/trip details of a booking.
     */
    @PutMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> updateBooking(
            @PathVariable Integer bookingId,
            @Valid @RequestBody BookingRequest request) {

        Booking booking = new Booking();

        // Customer Details
        booking.setCustomerFirstName(request.getCustomerFirstName());
        booking.setCustomerLastName(request.getCustomerLastName());
        booking.setCustomerContactPhone(request.getCustomerContactPhone());
        booking.setCustomerContactEmail(request.getCustomerContactEmail());
        booking.setCustomerAddress(request.getCustomerAddress());
        booking.setCustomerCity(request.getCustomerCity());

        // Trip Details
        booking.setStartDateTime(request.getStartDateTime());
        booking.setEndDateTime(request.getEndDateTime());
        booking.setStartLocation(request.getStartLocation());
        booking.setDestination(request.getDestination());
        booking.setPassengerCount(request.getPassengerCount());

        Booking updatedBooking =
                bookingService.updateBooking(bookingId, booking);

        return ResponseEntity.ok(toResponse(updatedBooking));
    }


    /**
     * Check which buses are available for the requested period.
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


    // =========================================================
    // OWNER
    // =========================================================

    /**
     * Get all bookings (owner).
     */
    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        return ResponseEntity.ok(
                toResponse(bookingService.getAllBookings())
        );
    }


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

        Booking booking = bookingService.getBooking(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found")
                );

        return ResponseEntity.ok(toResponse(booking));
    }


    /**
     * Owner sets the final price.
     * This enables payment for the customer.
     */
    @PutMapping("/{bookingId}/price")
    public ResponseEntity<BookingResponse> setBookingPrice(
            @PathVariable Integer bookingId,
            @RequestParam BigDecimal price) {

        return ResponseEntity.ok(
                toResponse(
                        bookingService.setPrice(bookingId, price)
                )
        );
    }

    @PutMapping("/{bookingId}/advance-amount")
    public ResponseEntity<BookingResponse> setAdvanceAmount(
            @PathVariable Integer bookingId,
            @RequestParam BigDecimal advanceAmount) {

        return ResponseEntity.ok(
                toResponse(
                        bookingService.setAdvanceAmount(bookingId, advanceAmount)
                )
        );
    }

    public BigDecimal parseAmount(String value) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException(
                    "Invalid amount: " + value
            );
        }
    }

    //POST http://localhost:8080/api/bookings/2/approve?paymentMethod=Card_Payment&driverID=DRV001&advanceAmount=8000.00&finalPrice=25000.00

    @PostMapping("/{bookingId}/approve")
    public ResponseEntity<BookingResponse> approveBooking(
            @PathVariable Integer bookingId,
            @RequestParam String paymentMethod,
            @RequestParam String driverID,
            @RequestParam String advanceAmount,
            @RequestParam String finalPrice) {

        return ResponseEntity.ok(
                toResponse(
                        bookingService.approveBooking(bookingId, paymentMethod, driverID, parseAmount(advanceAmount), parseAmount(finalPrice))
                )
        );
    }

    @PutMapping("/{bookingId}/bus")
    public ResponseEntity<BookingResponse> setBookingBus(
            @PathVariable Integer bookingId,
            @RequestParam String busRegistrationNo) {

        return ResponseEntity.ok(
                toResponse(
                        bookingService.assignBus(
                                bookingId,
                                busRegistrationNo
                        )
                )
        );
    }

    @PutMapping("/{bookingId}/driver")
    public ResponseEntity<BookingResponse> setBookingDriver(
            @PathVariable Integer bookingId,
            @RequestParam String driverID) {

        return ResponseEntity.ok(
                toResponse(
                        bookingService.assignDriver(
                                bookingId,
                                driverID
                        )
                )
        );
    }

    @PostMapping("/{bookingId}/confirm")
    public ResponseEntity<BookingResponse> confirmBooking(
            @PathVariable Integer bookingId) {

        return ResponseEntity.ok(
                toResponse(
                        bookingService.confirmBooking(bookingId)
                )
        );
    }

    @PostMapping("/{bookingId}/complete")
    public ResponseEntity<BookingResponse> completeBooking(
            @PathVariable Integer bookingId,
            @RequestParam BigDecimal startOdo,
            @RequestParam BigDecimal endOdo) {

        return ResponseEntity.ok(
                toResponse(
                        bookingService.completeBooking(bookingId, startOdo, endOdo)
                )
        );
    }

    @PostMapping("/{bookingId}/reject")
    public ResponseEntity<BookingResponse> rejectBooking(
            @PathVariable Integer bookingId) {

        return ResponseEntity.ok(
                toResponse(
                        bookingService.rejectBooking(bookingId)
                )
        );
    }

    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable Integer bookingId) {

        return ResponseEntity.ok(
                toResponse(
                        bookingService.cancelBooking(bookingId)
                )
        );
    }
}
