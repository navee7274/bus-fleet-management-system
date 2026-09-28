package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.entity.Booking;
import com.busfleetmanagement.system.entity.Bus;
import com.busfleetmanagement.system.entity.Driver;
import com.busfleetmanagement.system.enums.BookingStatus;
import com.busfleetmanagement.system.repository.BookingRepository;
import com.busfleetmanagement.system.repository.BusRepository;
import com.busfleetmanagement.system.repository.DriverRepository;
import com.busfleetmanagement.system.repository.JourneyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class  AvailabilityService {
    private final BusRepository busRepository;
    private final DriverRepository driverRepository;
    private final BookingRepository bookingRepository;

    public AvailabilityService(BusRepository busRepository, DriverRepository driverRepository, JourneyRepository journeyRepository, BookingRepository bookingRepository) {
        this.busRepository = busRepository;
        this.driverRepository = driverRepository;
        this.bookingRepository = bookingRepository;
    }

    // BUS AVAILABILITY--------------------------------------------
    public boolean isBusAvailable(Bus bus, LocalDateTime startDateTime, LocalDateTime endDateTime) {

        List<Booking> bookings =
                bookingRepository
                        .findByBusAndStartDateTimeLessThanAndEndDateTimeGreaterThan(
                                bus,
                                endDateTime,
                                startDateTime
                        );

        return bookings.stream()
                .noneMatch(booking ->
                        booking.getStatus() == BookingStatus.PENDING ||
                                booking.getStatus() == BookingStatus.PAYMENT_PENDING ||
                                booking.getStatus() == BookingStatus.CONFIRMED
                );
    }

    // DRIVER AVAILABILITY--------------------------------------------
    public boolean isDriverAvailable(Driver driver, LocalDateTime startDateTime, LocalDateTime endDateTime){
        List<Booking> bookings = bookingRepository.findByDriverAndStartDateTimeLessThanAndEndDateTimeGreaterThan(
                driver,
                endDateTime,
                startDateTime
        );
        return bookings.stream()
                .noneMatch(booking ->
                        booking.getStatus() == BookingStatus.PENDING ||
                                booking.getStatus() == BookingStatus.PAYMENT_PENDING ||
                                booking.getStatus() == BookingStatus.CONFIRMED
                );
    }

    // DRIVER & BUS AVAILABILITY--------------------------------------------
    public boolean isBusAndDriverAvailable(Bus bus, Driver driver, LocalDateTime startDateTime, LocalDateTime endDateTime) {

        return isBusAvailable(bus, startDateTime, endDateTime)
                && isDriverAvailable(driver, startDateTime, endDateTime);
    }

    // GET AVAILABLE BUSSES---------------------------------------------------
    public List<Bus> getAvailableBuses(LocalDateTime startDateTime, LocalDateTime endDateTime, int passengerCount) {

        List<Bus> buses = busRepository.findByActiveTrue();

        return buses.stream()
                .filter(bus -> isBusCapacityEnough(bus, passengerCount))
                .filter(bus -> isBusAvailable(
                        bus,
                        startDateTime,
                        endDateTime
                ))
                .toList();
    }

    // GET AVAILABLE Drivers---------------------------------------------------
    public List<Driver> getAvailableDrivers(LocalDateTime startDateTime, LocalDateTime endDateTime) {

        List<Driver> drivers = driverRepository.findAll();

        return drivers.stream()
                .filter(driver ->
                        isDriverAvailable(
                                driver,
                                startDateTime,
                                endDateTime
                        )
                )
                .toList();
    }

    // CHECK CAPACITY-----------------------------------------------------------
    public boolean isBusCapacityEnough(Bus bus, int passengerCount){
        return bus.getCapacity() >= passengerCount;
    }

    // COMPLETE AVAILABILITY CHECK-----------------------------------------------
    public boolean isAvailable(Driver driver, int passengerCount, Bus bus,LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (bus == null || driver == null) {
            return false;
        }
        if (startDateTime == null || endDateTime == null) {
            return false;
        }
        if(!startDateTime.isBefore(endDateTime)) {
            return false;
        }
        if(!bus.isActive()){
            return false;
        }
        if(!isBusCapacityEnough(bus, passengerCount)) {
            return false;
        }
        return isBusAndDriverAvailable(
                bus,
                driver,
                startDateTime,
                endDateTime
        );

    }
}