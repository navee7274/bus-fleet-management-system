package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.entity.Booking;
import com.busfleetmanagement.system.entity.Bus;
import com.busfleetmanagement.system.entity.Driver;
import com.busfleetmanagement.system.entity.Payment;
import com.busfleetmanagement.system.enums.BookingStatus;
import com.busfleetmanagement.system.enums.PaymentStatus;
import com.busfleetmanagement.system.exception.BadRequestException;
import com.busfleetmanagement.system.exception.ResourceNotFoundException;
import com.busfleetmanagement.system.repository.BookingRepository;
import com.busfleetmanagement.system.repository.BusRepository;
import com.busfleetmanagement.system.repository.DriverRepository;
import com.busfleetmanagement.system.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private BusRepository busRepository;
    private DriverRepository driverRepository;

    private PaymentService paymentService;


    public BookingService(BookingRepository bookingRepository, BusRepository busRepository, DriverRepository driverRepository, PaymentService paymentService){
        this.bookingRepository = bookingRepository;
        this.busRepository = busRepository;
        this.driverRepository = driverRepository;
        this.paymentService = paymentService;
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

    //////////////////////////////////////////////////////////////
    /////////////////////// CRUD FUNCTIONS ///////////////////////
    //////////////////////////////////////////////////////////////

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
    public Booking updateBooking(int BookingId, Booking booking) {

        Booking existingBooking = bookingRepository.findById(BookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Validate updated customer/trip details
        validateAll(booking);

        // Customer details
        existingBooking.setCustomerFirstName(booking.getCustomerFirstName());
        existingBooking.setCustomerLastName(booking.getCustomerLastName());
        existingBooking.setCustomerContactPhone(booking.getCustomerContactPhone());
        existingBooking.setCustomerContactEmail(booking.getCustomerContactEmail());
        existingBooking.setCustomerAddress(booking.getCustomerAddress());
        existingBooking.setCustomerCity(booking.getCustomerCity());

        // Journey details
        existingBooking.setStartDateTime(booking.getStartDateTime());
        existingBooking.setEndDateTime(booking.getEndDateTime());
        existingBooking.setStartLocation(booking.getStartLocation());
        existingBooking.setDestination(booking.getDestination());
        existingBooking.setPassengerCount(booking.getPassengerCount());

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

    //////////////////////////////////////////////////////////////
    //////////////////// BUSINESS FUNCTIONS //////////////////////
    //////////////////////////////////////////////////////////////

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

        System.out.println("Looking for: " + DriverID);

        Driver driver = driverRepository.findById(DriverID)
                .orElseThrow(()-> new ResourceNotFoundException("Driver not found"));

        System.out.println("Found: " + DriverID);

        existingBooking.setDriver(driver);

        return bookingRepository.save(existingBooking);
    }

    // SET PRICE
    public Booking setPrice(int BookingId, BigDecimal bookingPrice){
        Booking existingBooking = bookingRepository.findById(BookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if(existingBooking.getStatus() != BookingStatus.PENDING &&
                existingBooking.getStatus() != BookingStatus.PAYMENT_PENDING){
            throw new BadRequestException(
                    "Price cannot be changed in this state of a booking."
            );
        }

        existingBooking.setFinalPrice(bookingPrice);

        return bookingRepository.save(existingBooking);
    }

    // SET ADVANCE AMOUNT
    public Booking setAdvanceAmount(int BookingId, BigDecimal advanceAmount){
        Booking existingBooking = bookingRepository.findById(BookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if(existingBooking.getStatus() != BookingStatus.PENDING &&
                existingBooking.getStatus() != BookingStatus.PAYMENT_PENDING){
            throw new BadRequestException(
                    "Advance amount cannot be changed in this state of a booking."
            );
        }

        existingBooking.setAdvanceAmount(advanceAmount);

        return bookingRepository.save(existingBooking);
    }

    // APPROVE BOOKING
    public Booking approveBooking(int BookingID, String paymentMethod){
        Booking existingBooking = bookingRepository.findById(BookingID)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found"));

        if(existingBooking.getStatus() != BookingStatus.PENDING){
            throw new BadRequestException(
                    "Booking cannot be approved in this state."
            );
        }

        if(existingBooking.getDriver() == null || existingBooking.getBus() == null){
            throw new BadRequestException(
                    "Booking cannot be approved when driver or Bus is not assigned."
            );
        }

        if(!existingBooking.getDriver().isDActive() || !existingBooking.getBus().isActive()){
            throw new BadRequestException(
                    "Booking cannot be approved when driver or Bus is not active."
            );
        }

        if(existingBooking.getAdvanceAmount() == null ||
                existingBooking.getAdvanceAmount().compareTo(BigDecimal.ZERO) <= 0 ||
                existingBooking.getFinalPrice() == null ||
                existingBooking.getFinalPrice().compareTo(BigDecimal.ZERO) <= 0
        ){
            throw new BadRequestException(
                    "Booking cannot be approved when Advance amount/ final price is not set"
            );
        }


        String[] paymentMethods = {"Bank_Transfer", "Card_Payment", "Cash"};

        if(!Arrays.asList(paymentMethods).contains(paymentMethod) ){
            throw new BadRequestException(
                    "Invalid payment method."
            );
        }

        String paymentMethod_f = paymentMethod.replace("_", " ");

        Payment payment = new Payment();

        payment.setBooking(existingBooking);
        payment.setAmount(existingBooking.getAdvanceAmount());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentMethod(paymentMethod_f);
        payment.setPaymentStatus(PaymentStatus.PENDING);

        paymentService.createPaymentRecord(payment);

        existingBooking.setStatus(BookingStatus.PAYMENT_PENDING);

        return bookingRepository.save(existingBooking);
    }

    // CONFIRM BOOKING
    public Booking confirmBooking(int BookingID){
        Booking toConfirmBooking = bookingRepository.findById(BookingID)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found"));

        toConfirmBooking.setStatus(BookingStatus.CONFIRMED);

        return bookingRepository.save(toConfirmBooking);
    }

    // COMPLETE BOOKING
    public Booking completeBooking(int BookingID){
        Booking toCompleteBooking = bookingRepository.findById(BookingID)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found"));

        toCompleteBooking.setStatus(BookingStatus.COMPLETED);

        return bookingRepository.save(toCompleteBooking);
    }

    // REJECT BOOKING
    public Booking rejectBooking(int BookingID){
        Booking existingBooking = bookingRepository.findById(BookingID)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found"));

        existingBooking.setStatus(BookingStatus.REJECTED);

        return bookingRepository.save(existingBooking);
    }

    // CANCEL BOOKING
    public Booking cancelBooking(int BookingID){
        Booking toCancelBooking = bookingRepository.findById(BookingID)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found"));

        toCancelBooking.setStatus(BookingStatus.CANCELLED);

        return bookingRepository.save(toCancelBooking);
    }

}
