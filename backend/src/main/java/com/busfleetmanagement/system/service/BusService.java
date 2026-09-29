package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.entity.Bus;
import com.busfleetmanagement.system.entity.Driver;
import com.busfleetmanagement.system.repository.BusRepository;
import com.busfleetmanagement.system.repository.DriverRepository;

import java.util.List;
import java.util.Optional;

public class  BusService {
    private final BusRepository BusRepository;

    public BusService(BusRepository BusRepository) {
        this.BusRepository = BusRepository;
    }

    //CREAT BUS RECORD
    public Bus creatBus(Bus bus) {
        return BusRepository.save(bus);
    }

    // READ ALL Driver RECORDS
    public List<Bus> getAllBuses() {
        return BusRepository.findAll();
    }

    // READ ONLY ONE Bus RECORD
    public Optional<Bus> getBusById(String bRegistrationNo) {
        return BusRepository.findById(bRegistrationNo);
    }

    //UPDATE BUS.................................................
    public Bus updateBus(String bRegistrationNo, Bus bus) {
        Bus existingRecord = BusRepository.findById(bRegistrationNo)
                .orElseThrow(() -> new RuntimeException("Bus record not found"));

        existingRecord.setBusRegistrationNo(bus.getBusRegistrationNo());
        existingRecord.setPurchaseDate(bus.getPurchaseDate());
        existingRecord.setPurchasePrice(bus.getPurchasePrice());
        existingRecord.setCapacity(bus.getCapacity());
        existingRecord.setNotes(bus.getNotes());
        existingRecord.setActive(bus.isActive());

        return BusRepository.save(existingRecord);

    }

    //DEACTIVATE BUS..............................
    public Bus deactivateBus(String bRegistrationNo) {

        Bus existingRecord = BusRepository.findById(bRegistrationNo)
                .orElseThrow(() ->
                        new RuntimeException("Bus record not found"));

        existingRecord.setActive(false);

        return BusRepository.save(existingRecord);
    }

}