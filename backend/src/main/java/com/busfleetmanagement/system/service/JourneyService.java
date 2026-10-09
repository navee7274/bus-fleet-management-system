package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.entity.Bus;
import com.busfleetmanagement.system.entity.Driver;
import com.busfleetmanagement.system.entity.Journey;
import com.busfleetmanagement.system.repository.BusRepository;
import com.busfleetmanagement.system.repository.DriverRepository;
import com.busfleetmanagement.system.repository.JourneyRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
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

            throw new RuntimeException("Start and end odometer reading are required");
        }

        if(journey.getEndOdometer().compareTo(journey.getStartOdometer()) < 0){

            throw new RuntimeException("End odometer cannot be less than start odometer");
        }

        //Calculate KM travelled
        BigDecimal kmTravelled = journey.getEndOdometer().subtract(journey.getEndOdometer());

        journey.setKmTraveled(kmTravelled);

        //Set managed entities
        journey.setBus(bus);
        journey.setDriver(driver);

        return journeyRepository.save(journey);
    }

    //Update journey
    public Journey updateJourney(int journeyID, Journey journey){

        Journey existingJourney = getJourneyById(journeyID);

        existingJourney.setJourneyDate(journey.getJourneyDate());
        existingJourney.setPurpose(journey.getPurpose());
        existingJourney.setClientDestination(journey.getClientDestination());
        existingJourney.setStartOdometer(journey.getStartOdometer());
        existingJourney.setEndOdometer(journey.getEndOdometer());
        existingJourney.setIncomeAmount(journey.getIncomeAmount());
        existingJourney.setNotes(journey.getNotes());

        //Validate odometer reading
        if(existingJourney.getStartOdometer() == null || existingJourney.getEndOdometer() == null){
            throw new RuntimeException("Start and end odometer reading are required");
        }

        if(existingJourney.getEndOdometer().compareTo(existingJourney.getStartOdometer()) < 0){
            throw new RuntimeException("End odometer cannot be less than start odometer");
        }

        //Update bus if supplied
        if(journey.getBus() != null && journey.getBus().getBusRegistrationNo() != null) {

            Bus bus = busRepository.findById(journey.getBus().getBusRegistrationNo()).orElseThrow(() -> new RuntimeException("Bus not found"));

            existingJourney.setBus(bus);
        }

        //Update driver if supplied
        if(journey.getDriver() != null && journey.getDriver().getDriverID() != null){

            Driver driver = driverRepository.findById(journey.getDriver().getDriverID()).orElseThrow(() -> new RuntimeException("Driver not found"));

            existingJourney.setDriver(driver);
        }
        return journeyRepository.save(existingJourney);
    }

    //Delete journey
    public void deleteJourney(int journeyID){

        if(!journeyRepository.existsById(journeyID)){
            throw new RuntimeException("Journey not found with ID: " + journeyID);
        }

        journeyRepository.deleteById(journeyID);
    }

    //Get journey by date
    public List<Journey> getJourneyByDate(LocalDate date){

        return journeyRepository.findAll().stream().filter(journey -> journey.getJourneyDate().equals(date)).toList();
    }

    //Calculate KM travelled
    public BigDecimal calculateKmTravelled(
            BigDecimal startOdometer,
            BigDecimal endOdometer) {

        if(startOdometer == null || endOdometer == null){
            throw new RuntimeException("Odometer reading cannot be null");
        }

        if(endOdometer.compareTo(startOdometer) < 0){
            throw new RuntimeException("End odometer cannot be less than start odometer");
        }

        return endOdometer.subtract(startOdometer);
    }
}
