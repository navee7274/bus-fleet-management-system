package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.entity.Booking;
import com.busfleetmanagement.system.entity.Bus;
import com.busfleetmanagement.system.entity.Driver;
import com.busfleetmanagement.system.entity.Payment;
import com.busfleetmanagement.system.enums.BookingStatus;
import com.busfleetmanagement.system.enums.PaymentStatus;
import com.busfleetmanagement.system.exception.ResourceNotFoundException;
import com.busfleetmanagement.system.repository.BookingRepository;
import com.busfleetmanagement.system.repository.BusRepository;
import com.busfleetmanagement.system.repository.DriverRepository;
import com.busfleetmanagement.system.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private BusRepository busRepository;
    private DriverRepository driverRepository;
    private PaymentRepository paymentRepository;

    public BookingService(BookingRepository bookingRepository){
        this.bookingRepository = bookingRepository;
    }

    // VALIDATE PHONE NUMBER
    private void validatePhoneNumber(String phone){
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone number is required");
        }

        if (!phone.matches("^(07\\d{8}|\\+947\\d{8})$")) {
            throw new IllegalArgumentException(
                    "Invalid Sri Lankan phone number. Use 07XXXXXXXX or +947XXXXXXXX"
            );
        }
    }

    // VALIDATE EMAIL ADDRESS
    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email address");
        }
    }

    // VALIDATE ALL VALUES
    private void validateAll(Booking booking){
        String phone = booking.getCustomerContactPhone();
        String email = booking.getCustomerContactEmail();

        validateEmail(email);
        validatePhoneNumber(phone);
    }

    // CREATE BOOKING
    public Booking createBooking(Booking booking){
        // VALIDATING CUSTOMER DETAILS
        validateAll(booking);

        return bookingRepository.save(booking);
    }

    // READ ALL BOOKINGS
    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    // UPDATE BOOKING
    public Booking updateBooking(int BookingId, Booking booking){

        Booking existingBooking = bookingRepository.findById(BookingId)
                .orElseThrow(()-> new RuntimeException("Booking not found"));

        // VALIDATING CUSTOMER DETAILS
        validateAll(booking);

        existingBooking.setCustomerFirstName(booking.getCustomerFirstName());
        existingBooking.setCustomerLastName(booking.getCustomerLastName());
        existingBooking.setCustomerContactPhone(booking.getCustomerContactPhone());
        existingBooking.setCustomerContactEmail(booking.getCustomerContactEmail());
        existingBooking.setCustomerAddress(booking.getCustomerAddress());
        existingBooking.setCustomerCity(booking.getCustomerCity());
        existingBooking.setStartDateTime(booking.getStartDateTime());
        existingBooking.setAdvanceAmount(booking.getAdvanceAmount());
        existingBooking.setEndDateTime(booking.getEndDateTime());
        existingBooking.setStartLocation(booking.getStartLocation());
        existingBooking.setDestination(booking.getDestination());
        existingBooking.setPassengerCount(booking.getPassengerCount());
        existingBooking.setBus(booking.getBus());
        existingBooking.setDriver(booking.getDriver());
        existingBooking.setEstimatedCost(booking.getEstimatedCost());
        existingBooking.setFinalPrice(booking.getFinalPrice());
        existingBooking.setStatus(booking.getStatus());
        existingBooking.setCreatedAt(booking.getCreatedAt());

        return bookingRepository.save(existingBooking);
    }

    // GET BOOKING
    public Optional<Booking> getBooking(int BookingID){
        return bookingRepository.findById(BookingID);
    }

    // GET PENDING BOOKINGS
    public List<Booking> getPendingBookings(){
        return bookingRepository.findByStatus(BookingStatus.PENDING);
    }

    // ASSIGN BUS
    public Booking assignBus(int BookingID, String bRegistrationNo){
        Booking existingBooking = bookingRepository.findById(BookingID)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found"));

        Bus bus = busRepository.findById(bRegistrationNo)
                .orElseThrow(() -> new ResourceNotFoundException("Bus not found"));

        existingBooking.setBus(bus);

        return bookingRepository.save(existingBooking);
    }

    // ASSIGN DRIVER
    public Booking assignDriver(int BookingID, String DriverID){
        Booking existingBooking = bookingRepository.findById(BookingID)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found"));

        Driver driver = driverRepository.findById(DriverID)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        existingBooking.setDriver(driver);

        return bookingRepository.save(existingBooking);
    }

    // SET BOOKING PRICE
    public Booking setBookingPrice(int BookingID, BigDecimal bookingPrice){
        Booking existingBooking = bookingRepository.findById(BookingID)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found"));

        existingBooking.setFinalPrice(bookingPrice);

        return bookingRepository.save(existingBooking);
    }

    // APPROVE BOOKING
    public Booking approveBooking(int BookingID){
        Booking existingBooking = bookingRepository.findById(BookingID)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found"));

        existingBooking.setStatus(BookingStatus.PAYMENT_PENDING);

        return bookingRepository.save(existingBooking);
    }

    // REJECT BOOKING
    public Booking rejectBooking(int BookingID){
        Booking existingBooking = bookingRepository.findById(BookingID)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found"));

        existingBooking.setStatus(BookingStatus.REJECTED);

        return bookingRepository.save(existingBooking);
    }

    // CONFIRM BOOKING
    public Booking confirmBooking(int BookingID){
        Booking toConfirmBooking = bookingRepository.findById(BookingID)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found"));

        toConfirmBooking.setStatus(BookingStatus.CONFIRMED);

        return bookingRepository.save(toConfirmBooking);
    }

    // CANCEL BOOKING
    public Booking cancelBooking(int BookingID){
        Booking toCancelBooking = bookingRepository.findById(BookingID)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found"));

        toCancelBooking.setStatus(BookingStatus.CANCELLED);

        return bookingRepository.save(toCancelBooking);
    }

    // COMPLETE BOOKING
    public Booking completeBooking(int BookingID){
        Booking toCompleteBooking = bookingRepository.findById(BookingID)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found"));

        toCompleteBooking.setStatus(BookingStatus.COMPLETED);

        return bookingRepository.save(toCompleteBooking);
    }

    @Transactional
    public Booking makePayment(int BookingID) {

        Booking booking = bookingRepository.findById(BookingID)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getStatus() != BookingStatus.PAYMENT_PENDING) {
            throw new RuntimeException(
                    "Payment cannot be made for this booking"
            );
        }

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(booking.getAdvanceAmount());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        paymentRepository.save(payment);

        booking.setStatus(BookingStatus.CONFIRMED);

        return bookingRepository.save(booking);
    }
}
