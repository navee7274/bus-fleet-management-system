package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.entity.Bus;
import com.busfleetmanagement.system.entity.Driver;
import com.busfleetmanagement.system.entity.Journey;
import com.busfleetmanagement.system.repository.BusRepository;
import com.busfleetmanagement.system.repository.DriverRepository;
import com.busfleetmanagement.system.repository.JourneyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JourneyService {

    private final JourneyRepository journeyRepository;
    private final BusRepository busRepository;
    private final DriverRepository driverRepository;

    public JourneyService(
            JourneyRepository journeyRepository,
            BusRepository busRepository,
            DriverRepository driverRepository){

                this.journeyRepository = journeyRepository;
                this.busRepository = busRepository;
                this.driverRepository = driverRepository;
    }

    //Get all journeys
    public List<Journey> getAllJourneys() {
        return journeyRepository.findAll();
    }

    //Get journey by ID
    public Journey getJourneyById(int journeyID){
        return journeyRepository.findById(journeyID)
                .orElseThrow(() ->
                        new RuntimeException("Journey not found with ID: " + journeyID));
    }
    //Create a new journey
    public Journey createJourney(Journey journey){

        //Check bus
        if(journey.getBus() == null || journey.getBus().getBusRegistrationNo() == null){

            throw new RuntimeException("Bus is required");
        }

        Bus bus = busRepository
                .findById(journey.getBus().getBusRegistrationNo())
                .orElseThrow(() ->
                        new RuntimeException("Bus not found"));

        //Check driver
        if(journey.getDriver() == null || journey.getDriver().getDriverID() == null){

            throw new RuntimeException("Driver is required");
        }

        Driver driver = driverRepository
                .findById(journey.getDriver().getDriverID())
                .orElseThrow(() ->
                        new RuntimeException("Driver not found"));

        //Validate odometer readings
        if(journey.getStartOdometer() == null || journey.getEndOdometer() == null){

            throw new RuntimeException("Start and end odometer reading are required")
        }

        if(journey.getEndOdometer().compareTo(journey.getStartOdometer()) < 0){

            throw new RuntimeException("End odometer cannot be less than start odometer");
        }

        //


    }
}
