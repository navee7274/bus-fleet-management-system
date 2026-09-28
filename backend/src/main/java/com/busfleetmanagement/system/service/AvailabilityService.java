package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.entity.Bus;
import com.busfleetmanagement.system.entity.Driver;
import com.busfleetmanagement.system.entity.Journey;
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
    private final JourneyRepository journeyRepository;

    public AvailabilityService(BusRepository busRepository, DriverRepository driverRepository, JourneyRepository journeyRepository) {
        this.busRepository = busRepository;
        this.driverRepository = driverRepository;
        this.journeyRepository = journeyRepository;
    }

    //BUS AVAILABILITY--------------------------------------------
    public boolean isBusAvailable(Bus bus,
                                     LocalDateTime startDateTime,
                                     LocalDateTime endDateTime){

        List<Journey> journeys = journeyRepository.findByBusAndStartDateTimeLessThanAndEndDateTimeGreaterThan(
                bus,
                endDteTime,
                startDtaeTime
        );
        return journeys.isEmpty();
    }

    //DRIVER AVAILABILITY--------------------------------------------
    public boolean isDriverAvailable(Driver driver,
                                        LocalDateTime startDateTime,
                                        LocalDateTime endDateTime){
        List<Journey> journeys = journeyRepository.findByDriverAndStartDateTimeLessThanAndEndDateTimeGreaterThan(
                driver,
                endDteTime,
                startDtaeTime
        );
        return journeys.isEmpty();
    }

    //DRIVER & BUS AVAILABILITY--------------------------------------------
    public boolean isBusAndDriverAvailability(Bus bus,
                                              Driver driver,
                                        LocalDateTime startDateTime,
                                        LocalDateTime endDateTime){

        return isBusAvailable(bus, startDateTime, endDateTime)
                && isDriverAvailable(driver, startDateTime, endDateTime);
    }

    //GET AVAILABLE BUSSES---------------------------------------------------
    public List<Bus> getAvailableBuses(LocalDateTime startDateTime,
                                       LocalDateTime endDateTime){
        List<Bus> buses = busRepository.findByBactiveTrue();
        return buses.stream().filter(bus -> isBusAvailable(
                bus,
                startDateTime,
                endDateTime
        ))
                .toList();
    }

    //GET AVAILABLE Drivers---------------------------------------------------
    public List<Bus> getAvailableDrivers(LocalDateTime startDateTime,
                                       LocalDateTime endDateTime){
        List<Bus> driver = driverRepository.findAll();
        return driver.stream().filter(driver -> isDriverAvailable(
                        driver,
                        startDateTime,
                        endDateTime
                ))
                .toList();
    }

    //CHECK CAPACITY-----------------------------------------------------------
    public boolean isAvailable(
            Bus bus,
            int passengerCount){
        return bus.getBCapacity() >= passengerCount;
    }

    //COMPLETE AVAILABILITY CHECK-----------------------------------------------
    public boolean isAvailable(
            Driver driver,
            int passengerCount,
            Bus bus,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    ) {
        if (bus == null || driver == null) {
            return false;
        }
        if (startDateTime == null || endDateTime == null) {
            return false;
        }
        if(!startDateTime.isBefore(endDateTime)) {
            return false;
        }
        if(!bus.getBActive()){
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

