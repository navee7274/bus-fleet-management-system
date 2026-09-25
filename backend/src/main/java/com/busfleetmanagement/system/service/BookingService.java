package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.entity.Booking;
import com.busfleetmanagement.system.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

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

    // READ ONLY ONE BOOKING
    public Optional<Booking> getBookingById(int BookingID){
        return bookingRepository.findById(BookingID);
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
        existingBooking.setEstimatedCost(booking.getEstimatedCost());
        existingBooking.setFinalPrice(booking.getFinalPrice());
        existingBooking.setStatus(booking.getStatus());
        existingBooking.setCreatedAt(booking.getCreatedAt());

        return bookingRepository.save(existingBooking);
    }
}
