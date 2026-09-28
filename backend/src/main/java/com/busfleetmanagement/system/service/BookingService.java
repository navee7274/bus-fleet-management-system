package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.entity.Booking;
import com.busfleetmanagement.system.entity.Bus;
import com.busfleetmanagement.system.entity.Driver;
import com.busfleetmanagement.system.enums.BookingStatus;
import com.busfleetmanagement.system.exception.ResourceNotFoundException;
import com.busfleetmanagement.system.repository.BookingRepository;
import com.busfleetmanagement.system.repository.BusRepository;
import com.busfleetmanagement.system.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private BusRepository busRepository;
    private DriverRepository driverRepository;

    public BookingService(BookingRepository bookingRepository){
        this.bookingRepository = bookingRepository;
    }

    // CREATE BOOKING
    public Booking createBooking(Booking booking){
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

        existingBooking.setCustomerName(booking.getCustomerName());
        existingBooking.setCustomerContact(booking.getCustomerContact());
        existingBooking.setStartDateTime(booking.getStartDateTime());
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
}
